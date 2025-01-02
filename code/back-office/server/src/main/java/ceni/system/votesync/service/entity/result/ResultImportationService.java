package ceni.system.votesync.service.entity.result;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.dto.AlertBody;
import ceni.system.votesync.dto.request.result.UploadElectoralResultRequest;
import ceni.system.votesync.exception.InvalidElectoralResultException;
import ceni.system.votesync.model.entity.alert.Alert;
import ceni.system.votesync.model.entity.election.result.ImportedResult;
import ceni.system.votesync.model.entity.election.result.ImportedResultDetails;
import ceni.system.votesync.model.entity.election.result.ImportedResultImage;
import ceni.system.votesync.repository.entity.election.result.ImportedResultDetailsRepository;
import ceni.system.votesync.repository.entity.election.result.ImportedResultImageRepository;
import ceni.system.votesync.repository.entity.election.result.ImportedResultRepository;
import ceni.system.votesync.service.FileService;
import ceni.system.votesync.service.FileStorageService;
import ceni.system.votesync.service.NotificationService;
import ceni.system.votesync.service.entity.alert.AlertService;

@Service
public class ResultImportationService {

    private FileStorageService fileStorageService;
    private ImportedResultRepository importedResultRepository;
    private ImportedResultDetailsRepository importedResultDetailsRepository;
    private ImportedResultImageRepository importedResultImageRepository;

    private AlertService alertService;
    private NotificationService notificationService;

    public ResultImportationService(FileStorageService fileStorageService,
            ImportedResultRepository importedResultRepository,
            ImportedResultDetailsRepository importedResultDetailsRepository,
            ImportedResultImageRepository importedResultImageRepository,
            AlertService alertService,
            NotificationService notificationService) {
        this.fileStorageService = fileStorageService;
        this.importedResultRepository = importedResultRepository;
        this.importedResultDetailsRepository = importedResultDetailsRepository;
        this.importedResultImageRepository = importedResultImageRepository;
        this.alertService = alertService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void importResultFromDirectory(int electionId, File resultDirectory)
            throws InvalidFormatException, IOException {
        String pollingStationCode = resultDirectory.getName();
        ImportedResult result = new ImportedResult();
        List<ImportedResultDetails> details = new ArrayList<>();
        List<ImportedResultImage> images = new ArrayList<>();

        for (File image : resultDirectory.listFiles()) {
            if (FileService.isImage(image)) {
                ImportedResultImage resultImage = new ImportedResultImage();
                String imagePath = electionId + "/" + ElectoralResultUploadService.RESULT_FOLDER + "/"
                        + pollingStationCode
                        + "/"
                        + image.getName();
                this.fileStorageService.store(image, imagePath);
                resultImage.setElectionId(electionId);
                resultImage.setPollingStationCode(pollingStationCode);
                resultImage.setImagePath(imagePath);
                images.add(resultImage);
            }
        }

        File resultFile = new File(resultDirectory, pollingStationCode + ".xlsx");
        this.populateResultAndResultDetailsFromExcelFile(
                resultFile, electionId, pollingStationCode, result,
                details);

        this.checkImportedResultValidity(result, details);

        this.importedResultRepository.save(result);
        this.importedResultDetailsRepository.saveAll(details);
        this.importedResultImageRepository.saveAll(images);
    }

    private void populateResultAndResultDetailsFromExcelFile(File excelFile, Integer electionId,
            String pollingStationCode, ImportedResult result,
            List<ImportedResultDetails> details) throws InvalidFormatException, IOException {
        result.setElectionId(electionId);
        result.setPollingStationCode(pollingStationCode);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip header row

        Row votersStatRow = rowIterator.next();
        result.setVoters((int) votersStatRow.getCell(0).getNumericCellValue());
        result.setRegisteredVoters((int) votersStatRow.getCell(1).getNumericCellValue());
        rowIterator.next(); // Moving to the next row

        Row invalidVotesRow = rowIterator.next();
        result.setBlankVotes((int) invalidVotesRow.getCell(0).getNumericCellValue());
        result.setNullVotes((int) invalidVotesRow.getCell(1).getNumericCellValue());
        rowIterator.next(); // Moving to the next row

        Row peoleUnder36StatRow = rowIterator.next();
        result.setMaleUnder36((int) peoleUnder36StatRow.getCell(0).getNumericCellValue());
        result.setFemaleUnder36((int) peoleUnder36StatRow.getCell(1).getNumericCellValue());
        rowIterator.next(); // Moving to the next row

        Row people36AndOverStatRow = rowIterator.next();
        result.setMale36AndOver((int) people36AndOverStatRow.getCell(0).getNumericCellValue());
        result.setFemale36AndOver((int) people36AndOverStatRow.getCell(1).getNumericCellValue());
        rowIterator.next(); // Moving to the next row

        Row peopleWithDisabilitiesStatRow = rowIterator.next();
        result.setDisabledPeople(
                (int) peopleWithDisabilitiesStatRow.getCell(0).getNumericCellValue());
        result.setVisuallyImpairedPeople(
                (int) peopleWithDisabilitiesStatRow.getCell(1).getNumericCellValue());
        rowIterator.next(); // Moving to the next row

        while (rowIterator.hasNext()) {
            ImportedResultDetails detail = new ImportedResultDetails();
            detail.setElectionId(electionId);
            detail.setPollingStationCode(pollingStationCode);
            Row candidateVotesRow = rowIterator.next();
            int candidateNumber = (int) candidateVotesRow.getCell(0).getNumericCellValue();
            int candidateVotes = (int) candidateVotesRow.getCell(1).getNumericCellValue();
            if (candidateNumber == 0 && candidateVotes == 0) {
                // This is a bug from the library, sometimes it still do not throw exception
                // even if there are no more row
                break;
            }
            detail.setCandidateNumber(candidateNumber);
            detail.setVotes(candidateVotes);
            details.add(detail);
        }
        workbook.close();
    }

    private void checkImportedResultValidity(ImportedResult result, List<ImportedResultDetails> details) {
        UploadElectoralResultRequest request = new UploadElectoralResultRequest();
        request.setVoters(result.getVoters());
        request.setRegisteredVoters(result.getRegisteredVoters());
        request.setNulls(result.getNullVotes());
        request.setBlanks(result.getBlankVotes());
        request.setCandidates(details.stream()
                .collect(Collectors.toMap(ImportedResultDetails::getCandidateNumber, ImportedResultDetails::getVotes)));
        if (!request.isValid()) {
            Alert alert = this.alertService.createIncoherentDataAlert(result.getElectionId(),
                    result.getPollingStationCode(),
                    "Données de résultat incohérentes pour le bureau de vote ayant le code: "
                            + result.getPollingStationCode());
            this.alertService.saveAllAlerts(List.of(alert));
            this.notificationService
                    .sendAlert(new AlertBody(1, "Résultat incohérent. Source: " + result.getPollingStationCode()));
            throw new InvalidElectoralResultException("Resultat pour le code bureau de vote: "
                    + result.getPollingStationCode() + " est invalide.");
        }
    }
}
