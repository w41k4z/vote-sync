package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.result.LegislativeResultService;
import internship.project.election.service.impl.domain.result.LocalResultService;
import internship.project.election.service.impl.domain.result.PresidentialResultService;

@RequestMapping("/api/elections/results")
@RestController
public class ElectoralResultController {

    private PresidentialResultService presidentialResultService;
    private LegislativeResultService legislativeResultService;
    private LocalResultService localResultService;

    public ElectoralResultController(PresidentialResultService presidentialResultService,
            LegislativeResultService legislativeResultService, LocalResultService localResultService) {
        this.presidentialResultService = presidentialResultService;
        this.legislativeResultService = legislativeResultService;
        this.localResultService = localResultService;
    }

    @GetMapping("/polling-station")
    public ResponseEntity<ApiResponse> pollingStationResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("pollingStationResults", this.presidentialResultService.getPollingStationResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ApiResponse> fokontanyResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("fokontanyResults", this.presidentialResultService.getFokontanyResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/municipal")
    public ResponseEntity<ApiResponse> localMunicipalResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("municipalResults", this.localResultService.getMunicipalResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/communal")
    public ResponseEntity<ApiResponse> legislativeCommunalResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("communalResults", this.legislativeResultService.getCommunalResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/legislative/district")
    public ResponseEntity<ApiResponse> legislativeDistrictResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("districtResults", this.legislativeResultService.getCommunalResults(electionId));
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
        data.put("provincialResults", this.presidentialResultService.getProvincialResults(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/presidential/global")
    public ResponseEntity<ApiResponse> presidentialGlobalResults(@RequestParam Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("globalResults", this.presidentialResultService.getGlobalResult(electionId));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
