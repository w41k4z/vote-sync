package ceni.system.votesync.service.entity.result;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Transactional
    public void updateResult(Integer resultId, Integer blankVotes, Integer nullVotes, Integer registeredVoters,
            Map<Integer, Integer> resultDetails) {
        Result result = this.getResultById(resultId)
                .orElseThrow(
                        () -> new ElectoralResultNotFoundException("Result not found. Id: " + resultId));
        result.setBlankVotes(blankVotes);
        result.setNullVotes(nullVotes);
        result.setRegisteredVoters(registeredVoters);
        result.setStatus(Status.CLOSED);
        resultDetails.forEach((resultDetailId, votes) -> {
            this.electoralResultDetailsUploadRepository.updateById(resultDetailId, votes);
        });
        this.electoralResultUploadRepository.save(result);
    }

    @Transactional
    public void uploadResult(UploadElectoralResultRequest request, MultipartFile[] images) {
        this.checkResultValidity(request);
        Result result = Result.fromUploadElectoralResultRequest(request);
        this.electoralResultUploadRepository.save(result);
        List<ImportedResultDetails> details = ImportedResultDetails.fromUploadElectoralResultRequestAndResultId(request,
                result.getId());
        this.electoralResultDetailsUploadRepository.saveAll(details);
        for (MultipartFile image : images) {
            ResultImage resultImage = new ResultImage();
            resultImage.setResultId(result.getId());
            String imagePath = request.getElectionId() + "/" + RESULT_FOLDER + "/" + result.getPollingStationId() + "/"
                    + image.getOriginalFilename();
            resultImage.setImagePath(imagePath);
            this.fileStorageService.store(image, imagePath);
            this.electoralResultImageUploadRepository.save(resultImage);
        }
        this.electoralResultDetailsUploadRepository.importElectoralResultDetails();
    }

    public void checkResultValidity(ElectoralResultRequest request) {
        boolean isValid = request.isValid();
        if (!isValid) {
            throw new InvalidElectoralResultException("Invalid result. Total votes do not match registered voters");
        }
    }
}
