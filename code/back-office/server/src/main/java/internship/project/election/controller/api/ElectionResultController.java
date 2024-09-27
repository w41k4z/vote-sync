package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.result.LegislativeResultService;
import internship.project.election.service.impl.domain.result.LocalResultService;
import internship.project.election.service.impl.domain.result.PresidentialResultService;

@RequestMapping("/api/elections/results")
@RestController
public class ElectionResultController {

    private PresidentialResultService presidentialResultService;
    private LegislativeResultService legislativeResultService;
    private LocalResultService localResultService;

    public ElectionResultController(PresidentialResultService presidentialResultService,
            LegislativeResultService legislativeResultService, LocalResultService localResultService) {
        this.presidentialResultService = presidentialResultService;
        this.legislativeResultService = legislativeResultService;
        this.localResultService = localResultService;
    }

    @GetMapping("/polling-station")
    public ResponseEntity<ApiResponse> pollingStationResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("pollingStationResults", this.presidentialResultService.getPollingStationResults());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ApiResponse> fokontanyResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("fokontanyResults", this.presidentialResultService.getFokontanyResults());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/municipal")
    public ResponseEntity<ApiResponse> localMunicipalResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("municipalResults", this.localResultService.getMunicipalResults());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/communal")
    public ResponseEntity<ApiResponse> legislativeCommunalResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("communalResults", this.legislativeResultService.getCommunalResults());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/district")
    public ResponseEntity<ApiResponse> legislativeDistrictResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("districtResults", this.legislativeResultService.getCommunalResults());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/communal")
    public ResponseEntity<ApiResponse> presidentialCommunalResults() {
        return this.legislativeCommunalResults();
    }

    @GetMapping("/presidential/district")
    public ResponseEntity<ApiResponse> presidentialDistrictResults() {
        return this.legislativeDistrictResults();
    }

    @GetMapping("/presidential/provincial")
    public ResponseEntity<ApiResponse> presidentialProvincialResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("provincialResults", this.presidentialResultService.getProvincialResults());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/global")
    public ResponseEntity<ApiResponse> presidentialGlobalResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("globalResults", this.presidentialResultService.getGlobalResult());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
