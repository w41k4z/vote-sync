package ceni.system.votesync.service.entity.result;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.config.SystemUserDetails;
import ceni.system.votesync.dto.AlertBody;
import ceni.system.votesync.dto.request.result.ElectoralResultRequest;
import ceni.system.votesync.dto.request.result.UploadElectoralResultRequest;
import ceni.system.votesync.dto.request.result.ValidateElectoralResultRequest;
import ceni.system.votesync.exception.ElectoralResultNotFoundException;
import ceni.system.votesync.exception.InvalidElectoralResultException;
import ceni.system.votesync.model.entity.election.result.Result;
import ceni.system.votesync.model.entity.alert.Alert;
import ceni.system.votesync.model.entity.election.result.ImportedResultDetails;
import ceni.system.votesync.model.entity.election.result.ResultImage;
import ceni.system.votesync.model.view.election.PollingStationVotersStat;
import ceni.system.votesync.repository.entity.election.result.ImportedResultDetailsRepository;
import ceni.system.votesync.repository.entity.election.result.ImportedResultRepository;
import ceni.system.votesync.repository.entity.election.result.ElectoralResultImageUploadRepository;
import ceni.system.votesync.repository.entity.election.result.ElectoralResultUploadRepository;
import ceni.system.votesync.service.FileStorageService;
import ceni.system.votesync.service.NotificationService;
import ceni.system.votesync.service.entity.alert.AlertService;
import ceni.system.votesync.service.entity.election.PollingStationVotersStatService;
import ceni.system.votesync.service.spec.auth.AuthService;

@Service
public class ElectoralResultUploadService {

    public static final String RESULT_FOLDER = "rs";
    public static final String IMPORT_FOLDER = "imp";

    private ImportedResultRepository importedResultRepository;
    private ImportedResultDetailsRepository importedResultDetailsRepository;
    private ResultImportationService resultImportationService;
    private ElectoralResultUploadRepository electoralResultUploadRepository;
    private ElectoralResultImageUploadRepository electoralResultImageUploadRepository;
    private PollingStationVotersStatService pollingStationVotersStatService;
    private AlertService alertService;
    private NotificationService notificationService;
    private FileStorageService fileStorageService;

    public ElectoralResultUploadService(
            ImportedResultRepository importedResultRepository,
            ElectoralResultUploadRepository electoralResultUploadRepository,
            ResultImportationService resultImportationService,
            ImportedResultDetailsRepository importedResultDetailsRepository,
            ElectoralResultImageUploadRepository electoralResultImageUploadRepository,
            PollingStationVotersStatService pollingStationVotersStatService,
            AlertService alertService,
            NotificationService notificationService,
            FileStorageService fileStorageService) {
        this.importedResultRepository = importedResultRepository;
        this.electoralResultUploadRepository = electoralResultUploadRepository;
        this.resultImportationService = resultImportationService;
        this.importedResultDetailsRepository = importedResultDetailsRepository;
        this.electoralResultImageUploadRepository = electoralResultImageUploadRepository;
        this.pollingStationVotersStatService = pollingStationVotersStatService;
        this.alertService = alertService;
        this.notificationService = notificationService;
        this.fileStorageService = fileStorageService;
    }

    public Optional<Result> getResultById(Integer resultId) {
        return this.electoralResultUploadRepository.findById(resultId);
    }

    public Map<String, Exception> importResults(Integer electionId, MultipartFile zipFile, String password)
            throws IOException {
        SystemUserDetails currentUser = AuthService.getActiveUser();
        String destinationFolder = electionId + "/" + IMPORT_FOLDER + "/"
                + Timestamp.valueOf(LocalDateTime.now()).toString().replace(":", "-") + "_" + currentUser.getName()
                + "_"
                + currentUser.getFirstName();
        File extractedDirectory = this.fileStorageService.unzipAndStore(destinationFolder, zipFile, password);
        try {
            HashMap<String, Exception> exceptions = new HashMap<>();
            List<Alert> alerts = new ArrayList<>();
            int imported = 0;
            for (File f : extractedDirectory.listFiles()) {
                try {
                    String pollingStationCode = f.getName();
                    this.importResultFromDirectory(electionId, f);
                    alerts.add(this.alertService.createUnsyncedDataAlert(electionId, pollingStationCode,
                            "Les résultats sont présentes, mais aucun enregistrement d'électeur."));
                    imported++;
                } catch (Exception e) {
                    exceptions.put(f.getName(), e);
                    e.printStackTrace();
                }
            }
            // migrating imported results to the main results table
            this.importedResultRepository.importElectoralResults(electionId);
            this.alertService.saveAllAlerts(alerts);

            String message = imported > 1 ? imported + " résultats sans enregistrements des électeurs"
                    : imported + " résultat sans enregistrements des électeurs";
            AlertBody alertBody = new AlertBody(imported, message);
            this.notificationService.sendAlert(alertBody);
            return exceptions;
        } catch (Exception err) {
            throw err;
        } finally {
            // deleting imported results whatever the result
            this.importedResultRepository.clearImportedElectoralResults(electionId);
        }
    }

    private void importResultFromDirectory(int electionId, File resultDirectory)
            throws InvalidFormatException, IOException {
        this.resultImportationService.importResultFromDirectory(electionId, resultDirectory);
    }

    public void uploadResult(UploadElectoralResultRequest request, MultipartFile[] images) {
        try {
            this.checkResultValidity(request);
        } catch (InvalidElectoralResultException e) {
            Alert alert = this.alertService.createIncoherentDataAlert(request.getElectionId(),
                    request.getPollingStationCode(),
                    "Données de résultat incohérentes pour le bureau de vote ayant le code: "
                            + request.getPollingStationCode());
            this.alertService.saveAllAlerts(List.of(alert));
            this.notificationService
                    .sendAlert(new AlertBody(1, "Résultat incohérent. Source: " + request.getPollingStationCode()));
            throw e;
        }
        try {
            this.saveElectoralResult(request, images);
        } catch (DataIntegrityViolationException e) {
            this.reuploadAndSaveElectoralResult(request, images);
        }
    }

    @Transactional
    public void saveElectoralResult(UploadElectoralResultRequest request, MultipartFile[] images) {
        Result result = Result.fromUploadElectoralResultRequest(request);
        PollingStationVotersStat pollingStationVotersStat = this.pollingStationVotersStatService
                .getPollingStationVotersStatByElectionIdAndPollingStationId(request.getElectionId(),
                        request.getPollingStationId())
                .orElseThrow(() -> new ElectoralResultNotFoundException(
                        "Polling station voters stat not found. Election id: " + request.getElectionId()
                                + ", polling station id: " + request.getPollingStationId()));
        int registeredVoters = pollingStationVotersStat.getMale36AndOver()
                + pollingStationVotersStat.getFemale36AndOver()
                + pollingStationVotersStat.getMaleUnder36() + pollingStationVotersStat.getFemaleUnder36();
        if (pollingStationVotersStat.getVoters() != request.getVoters()
                || registeredVoters != request.getTotalVotes()) {
            throw new InvalidElectoralResultException(
                    "Invalid result. Synced data does not match with the uploaded result. Synced voters: "
                            + pollingStationVotersStat.getVoters() + ", uploaded voters: " + request.getVoters()
                            + ", synced registered voters: " + registeredVoters + ", uploaded registered voters: "
                            + request.getTotalVotes());
        }
        result.setImported(0);
        result.setStatus(Status.CLOSED);
        result.setMaleUnder36(pollingStationVotersStat.getMaleUnder36());
        result.setFemaleUnder36(pollingStationVotersStat.getFemaleUnder36());
        result.setMale36AndOver(pollingStationVotersStat.getMale36AndOver());
        result.setFemale36AndOver(pollingStationVotersStat.getFemale36AndOver());
        result.setDisabledPeople(pollingStationVotersStat.getDisabledPeople());
        result.setVisuallyImpairedPeople(pollingStationVotersStat.getVisuallyImpairedPeople());
        this.electoralResultUploadRepository.save(result);
        List<ImportedResultDetails> details = ImportedResultDetails.fromUploadElectoralResultRequestAndResultId(
                request);
        // saving details in details_resultats_importes table
        this.importedResultDetailsRepository.saveAll(details);
        /*
         * importing result changes details from details_resultats_importes to
         * details_resultats
         */
        this.importedResultDetailsRepository.importElectoralResultDetails(request.getElectionId());
        // deleting imported result details after migration
        this.importedResultDetailsRepository.deleteAll(details);
        for (MultipartFile image : images) {
            ResultImage resultImage = new ResultImage();
            resultImage.setResultId(result.getId());
            String imagePath = request.getElectionId() + "/" + RESULT_FOLDER + "/" + request.getPollingStationCode()
                    + "/"
                    + image.getOriginalFilename();
            resultImage.setImagePath(imagePath);
            this.fileStorageService.store(image, imagePath);
            this.electoralResultImageUploadRepository.save(resultImage);
        }
    }

    /*
     * Result reupload must only concerns blank votes, null votes, candidates votes
     * and images.
     * THe number of voters can not be changed
     */
    @Transactional
    public void reuploadAndSaveElectoralResult(UploadElectoralResultRequest request, MultipartFile[] images) {
        Result resultExample = new Result();
        resultExample.setElectionId(request.getElectionId());
        resultExample.setPollingStationId(request.getPollingStationId());
        Result result = this.electoralResultUploadRepository.findOne(Example.of(resultExample))
                .orElseThrow(() -> new ElectoralResultNotFoundException(
                        "Result not found. Election id: " + request.getElectionId() + ", polling station id: "
                                + request.getPollingStationId()));
        if (result.getStatus() == Status.CLOSED) {
            throw new InvalidElectoralResultException("Result already validated. Cannot update anymore");
        }
        result.setBlankVotes(request.getBlanks());
        result.setNullVotes(request.getNulls());
        this.electoralResultUploadRepository.save(result);
        List<ImportedResultDetails> details = ImportedResultDetails
                .fromUploadElectoralResultRequestAndResultId(request);
        // saving details in details_resultats_importes table
        this.importedResultDetailsRepository.saveAll(details);
        /*
         * importing result changes details from details_resultats_importes to
         * details_resultats
         */
        this.importedResultDetailsRepository.importElectoralResultDetails(request.getElectionId());
        // deleting imported result details after migration
        this.importedResultDetailsRepository.deleteAll(details);
        // deleting previous images
        this.electoralResultImageUploadRepository
                .delete(ElectoralResultSpecification.imagesWithResultId(result.getId()));
        for (MultipartFile image : images) {
            ResultImage resultImage = new ResultImage();
            resultImage.setResultId(result.getId());
            String imagePath = result.getElectionId() + "/" + RESULT_FOLDER + "/" + result.getPollingStationId()
                    + "/"
                    + image.getOriginalFilename();
            resultImage.setImagePath(imagePath);
            this.fileStorageService.store(image, imagePath);
            this.electoralResultImageUploadRepository.save(resultImage);
        }
    }

    public void invalidateElectoralResult(Integer electionId, Integer pollingStationId) {
        this.electoralResultUploadRepository.updateResultState(Status.PENDING, electionId, pollingStationId);
    }

    @Transactional
    public void validateElectoralResult(ValidateElectoralResultRequest request) {
        this.checkResultValidity(request);
        Result result = this.getResultById(request.getResultId())
                .orElseThrow(
                        () -> new ElectoralResultNotFoundException("Result not found. Id: " + request.getResultId()));
        if (result.getStatus() == Status.CLOSED) {
            throw new InvalidElectoralResultException("Result already validated. Cannot update anymore");
        }
        result.setBlankVotes(request.getBlanks());
        result.setNullVotes(request.getNulls());
        result.setMale36AndOver(request.getMale36AndOver());
        result.setFemale36AndOver(request.getFemale36AndOver());
        result.setMaleUnder36(request.getMaleUnder36());
        result.setFemaleUnder36(request.getFemaleUnder36());
        result.setDisabledPeople(request.getDisabledPeople());
        result.setVisuallyImpairedPeople(request.getVisuallyImpairedPeople());
        result.setVoters(request.getVoters());
        result.setStatus(Status.CLOSED);
        this.electoralResultUploadRepository.save(result);
        request.getCandidates().forEach((resultDetailId, votes) -> {
            this.importedResultDetailsRepository.updateById(resultDetailId, votes);
        });
    }

    private void checkResultValidity(ElectoralResultRequest request) {
        boolean isValid = request.isValid();
        if (!isValid) {
            throw new InvalidElectoralResultException("Invalid result. Total votes do not match registered voters");
        }
    }
}
