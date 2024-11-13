package ceni.system.votesync.service.entity.result;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.dto.request.result.ElectoralResultRequest;
import ceni.system.votesync.dto.request.result.UploadElectoralResultRequest;
import ceni.system.votesync.exception.ElectoralResultNotFoundException;
import ceni.system.votesync.exception.InvalidElectoralResultException;
import ceni.system.votesync.model.entity.election.result.Result;
import ceni.system.votesync.model.entity.election.result.ImportedResultDetails;
import ceni.system.votesync.model.entity.election.result.ResultImage;
import ceni.system.votesync.repository.entity.election.result.ElectoralResultDetailsUploadRepository;
import ceni.system.votesync.repository.entity.election.result.ElectoralResultImageUploadRepository;
import ceni.system.votesync.repository.entity.election.result.ElectoralResultUploadRepository;
import ceni.system.votesync.service.FileStorageService;

@Service
public class ElectoralResultUploadService {

    private static final String RESULT_FOLDER = "rs";

    private ElectoralResultUploadRepository electoralResultUploadRepository;
    private ElectoralResultDetailsUploadRepository electoralResultDetailsUploadRepository;
    private ElectoralResultImageUploadRepository electoralResultImageUploadRepository;
    private FileStorageService fileStorageService;

    public ElectoralResultUploadService(ElectoralResultUploadRepository electoralResultUploadRepository,
            ElectoralResultDetailsUploadRepository electoralResultDetailsUploadRepository,
            ElectoralResultImageUploadRepository electoralResultImageUploadRepository,
            FileStorageService fileStorageService) {
        this.electoralResultUploadRepository = electoralResultUploadRepository;
        this.electoralResultDetailsUploadRepository = electoralResultDetailsUploadRepository;
        this.electoralResultImageUploadRepository = electoralResultImageUploadRepository;
        this.fileStorageService = fileStorageService;
    }

    public Optional<Result> getResultById(Integer resultId) {
        return this.electoralResultUploadRepository.findById(resultId);
    }

    public void uploadResult(UploadElectoralResultRequest request, MultipartFile[] images) {
        this.checkResultValidity(request);
        try {
            this.saveElectoralResult(request, images);
        } catch (DataIntegrityViolationException e) {
            this.reuploadAndSaveElectoralResult(request, images);
        }
    }

    @Transactional
    private void saveElectoralResult(UploadElectoralResultRequest request, MultipartFile[] images) {
        Result result = Result.fromUploadElectoralResultRequest(request);
        this.electoralResultUploadRepository.save(result);
        List<ImportedResultDetails> details = ImportedResultDetails.fromUploadElectoralResultRequestAndResultId(
                request,
                result.getId());
        // saving details in details_resultats_importes table
        this.electoralResultDetailsUploadRepository.saveAll(details);
        /*
         * importing result changes details from details_resultats_importes to
         * details_resultats
         */
        this.electoralResultDetailsUploadRepository.importElectoralResultDetails();
        // deleting imported details after table migration
        this.electoralResultDetailsUploadRepository.deleteAll(details);
        for (MultipartFile image : images) {
            ResultImage resultImage = new ResultImage();
            resultImage.setResultId(result.getId());
            String imagePath = request.getElectionId() + "/" + RESULT_FOLDER + "/" + request.getPollingStationId()
                    + "/"
                    + image.getOriginalFilename();
            resultImage.setImagePath(imagePath);
            this.fileStorageService.store(image, imagePath);
            this.electoralResultImageUploadRepository.save(resultImage);
        }
    }

    @Transactional
    private void reuploadAndSaveElectoralResult(UploadElectoralResultRequest request, MultipartFile[] images) {
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
        result.setRegisteredVoters(request.getRegistered());
        this.electoralResultUploadRepository.save(result);
        List<ImportedResultDetails> details = ImportedResultDetails.fromUploadElectoralResultRequestAndResultId(
                request,
                result.getId());
        // saving details in details_resultats_importes table
        this.electoralResultDetailsUploadRepository.saveAll(details);
        /*
         * importing result changes details from details_resultats_importes to
         * details_resultats
         */
        this.electoralResultDetailsUploadRepository.importElectoralResultDetails();
        // deleting imported details after table migration
        this.electoralResultDetailsUploadRepository.deleteAll(details);
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

    @Transactional
    public void validateElectoralResult(Integer resultId, Integer blankVotes, Integer nullVotes,
            Integer registeredVoters,
            Map<Integer, Integer> resultDetails) {
        Result result = this.getResultById(resultId)
                .orElseThrow(
                        () -> new ElectoralResultNotFoundException("Result not found. Id: " + resultId));
        if (result.getStatus() == Status.CLOSED) {
            throw new InvalidElectoralResultException("Result already validated. Cannot update anymore");
        }
        result.setBlankVotes(blankVotes);
        result.setNullVotes(nullVotes);
        result.setRegisteredVoters(registeredVoters);
        result.setStatus(Status.CLOSED);
        this.electoralResultUploadRepository.save(result);
        resultDetails.forEach((resultDetailId, votes) -> {
            this.electoralResultDetailsUploadRepository.updateById(resultDetailId, votes);
        });
    }

    public void checkResultValidity(ElectoralResultRequest request) {
        boolean isValid = request.isValid();
        if (!isValid) {
            throw new InvalidElectoralResultException("Invalid result. Total votes do not match registered voters");
        }
    }
}
