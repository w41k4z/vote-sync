package ceni.system.votesync.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import ceni.system.votesync.dto.request.result.InvalidateElectoralResultRequest;
import ceni.system.votesync.dto.request.result.ValidateQrCodeRequest;
import ceni.system.votesync.service.entity.election.QrCodeService;
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
    private QrCodeService qrCodeService;

    public ElectoralResultController(PresidentialResultService presidentialResultService,
            LegislativeResultService legislativeResultService, LocalResultService localResultService,
            ElectoralResultUploadService electoralResultUploadService,
            PendingElectoralResultService pendingElectoralResultService,
            QrCodeService qrCodeService) {
        this.presidentialResultService = presidentialResultService;
        this.legislativeResultService = legislativeResultService;
        this.localResultService = localResultService;
        this.electoralResultUploadService = electoralResultUploadService;
        this.pendingElectoralResultService = pendingElectoralResultService;
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/validate-qr-code")
    public ResponseEntity<ApiResponse> validateQrCode(@RequestBody ValidateQrCodeRequest request) {
        try {
            this.qrCodeService.validateQrCode(request.getQrCode());
            this.qrCodeService.invalidateQrCode(request.getQrCode());
            return ResponseEntity.ok(new ApiResponse(null, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadResult(@RequestParam String result,
            @RequestPart MultipartFile[] images) {
        Gson gson = new Gson();
        UploadElectoralResultRequest resultObject = gson.fromJson(result, UploadElectoralResultRequest.class);
        this.electoralResultUploadService.uploadResult(resultObject, images);
        return ResponseEntity.ok(new ApiResponse(null, "Resultat enregistré avec succès"));
    }

    @PostMapping("/import")
    public ResponseEntity<ApiResponse> importResults(
            @RequestParam(required = true) Integer electionId, @RequestParam(required = true) MultipartFile file,
            @RequestParam(required = true) String password) throws IOException {
        Map<String, Exception> errors = this.electoralResultUploadService.importResults(electionId, file, password);
        String message = "Resultats importés avec " + errors.size() + " erreur(s).";
        return ResponseEntity
                .ok(new ApiResponse(errors,
                        message));
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
        return ResponseEntity.ok(new ApiResponse(null, "Result validé"));
    }

    @PutMapping("/invalidate")
    public ResponseEntity<ApiResponse> invalidateResult(@RequestBody InvalidateElectoralResultRequest request) {
        this.electoralResultUploadService.invalidateElectoralResult(request.getElectionId(),
                request.getPollingStationId());
        return ResponseEntity.ok(new ApiResponse(null, "Le résultat a été invalidé"));
    }

    @GetMapping("/polling-station")
    public ResponseEntity<ApiResponse> pollingStationResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId,
            @RequestParam(required = false) Integer fokontanyId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getPollingStationResults(electionId, regionId,
                districtId, communeId, fokontanyId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ApiResponse> fokontanyResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getFokontanyResults(electionId, regionId,
                districtId, communeId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/polling-station")
    public ResponseEntity<ApiResponse> pollingStationLocalElectionResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer municipalityId,
            @RequestParam(required = false) Integer fokontanyId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults",
                this.localResultService.getPollingStationLocalElectionResults(electionId, regionId, districtId,
                        municipalityId, fokontanyId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/fokontany")
    public ResponseEntity<ApiResponse> fokontanyLocalElectionResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer municipalityId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.localResultService.getFokontanyLocalElectionResults(electionId, regionId,
                districtId, municipalityId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/municipal")
    public ResponseEntity<ApiResponse> localMunicipalResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults",
                this.localResultService.getMunicipalResults(electionId, regionId, districtId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/communal")
    public ResponseEntity<ApiResponse> legislativeCommunalResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults",
                this.legislativeResultService.getCommunalResults(electionId, regionId, districtId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/district")
    public ResponseEntity<ApiResponse> legislativeDistrictResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.legislativeResultService.getDistrictResults(electionId, regionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/regional")
    public ResponseEntity<ApiResponse> presidentialRegionalResults(@RequestParam Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getRegionalResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/provincial")
    public ResponseEntity<ApiResponse> presidentialProvincialResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getProvincialResults(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/global")
    public ResponseEntity<ApiResponse> presidentialGlobalResults(@RequestParam Integer electionId,
            @PageableDefault(value = 1, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("electoralResults", this.presidentialResultService.getGlobalResult(electionId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
