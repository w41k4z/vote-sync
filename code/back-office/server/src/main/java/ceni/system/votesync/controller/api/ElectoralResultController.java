package ceni.system.votesync.controller.api;

import java.util.HashMap;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import ceni.system.votesync.config.Pagination;
import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.dto.request.result.UploadElectoralResultRequest;
import ceni.system.votesync.dto.request.result.ValidateElectoralResultRequest;
import ceni.system.votesync.service.entity.result.ElectoralResultUploadService;
import ceni.system.votesync.service.entity.result.LegislativeResultService;
import ceni.system.votesync.service.entity.result.LocalResultService;
import ceni.system.votesync.service.entity.result.PendingElectoralResultService;
import ceni.system.votesync.service.entity.result.PresidentialResultService;

@RequestMapping("/api/elections/results")
@RestController
public class ElectoralResultController {

    private PresidentialResultService presidentialResultService;
    private LegislativeResultService legislativeResultService;
    private LocalResultService localResultService;
    private ElectoralResultUploadService electoralResultUploadService;
    private PendingElectoralResultService pendingElectoralResultService;

    public ElectoralResultController(PresidentialResultService presidentialResultService,
            LegislativeResultService legislativeResultService, LocalResultService localResultService,
            ElectoralResultUploadService electoralResultUploadService,
            PendingElectoralResultService pendingElectoralResultService) {
        this.presidentialResultService = presidentialResultService;
        this.legislativeResultService = legislativeResultService;
        this.localResultService = localResultService;
        this.electoralResultUploadService = electoralResultUploadService;
        this.pendingElectoralResultService = pendingElectoralResultService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadResult(@RequestParam String result,
            @RequestPart MultipartFile[] images) {
        Gson gson = new Gson();
        UploadElectoralResultRequest resultObject = gson.fromJson(result, UploadElectoralResultRequest.class);
        this.electoralResultUploadService.uploadResult(resultObject, images);
        return ResponseEntity.ok(new ApiResponse(null, "Result uploaded successfully"));
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse> pendingResults(
            @PageableDefault(size = Pagination.DEFAULT_SIZE, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults",
                this.pendingElectoralResultService.getPendingElectoralResults(pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateResult(@RequestBody ValidateElectoralResultRequest request) {
        this.electoralResultUploadService.validateElectoralResult(request);
        return ResponseEntity.ok(new ApiResponse(null, "Result validated successfully"));
    }

    @GetMapping("/polling-station")
    public ResponseEntity<ApiResponse> pollingStationResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getPollingStationResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ApiResponse> fokontanyResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getFokontanyResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/polling-station")
    public ResponseEntity<ApiResponse> pollingStationLocalElectionResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults",
                this.localResultService.getPollingStationLocalElectionResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/fokontany")
    public ResponseEntity<ApiResponse> fokontanyLocalElectionResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.localResultService.getFokontanyLocalElectionResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/municipal")
    public ResponseEntity<ApiResponse> localMunicipalResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.localResultService.getMunicipalResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/communal")
    public ResponseEntity<ApiResponse> legislativeCommunalResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.legislativeResultService.getCommunalResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/district")
    public ResponseEntity<ApiResponse> legislativeDistrictResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.legislativeResultService.getCommunalResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/communal")
    public ResponseEntity<ApiResponse> presidentialCommunalResults(@RequestParam Integer electionId) {
        return this.legislativeCommunalResults(electionId);
    }

    @GetMapping("/presidential/district")
    public ResponseEntity<ApiResponse> presidentialDistrictResults(@RequestParam Integer electionId) {
        return this.legislativeDistrictResults(electionId);
    }

    @GetMapping("/presidential/provincial")
    public ResponseEntity<ApiResponse> presidentialProvincialResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getProvincialResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/global")
    public ResponseEntity<ApiResponse> presidentialGlobalResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getGlobalResult(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
