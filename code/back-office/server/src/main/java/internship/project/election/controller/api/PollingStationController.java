package internship.project.election.controller.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

import internship.project.election.config.Pagination;
import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.NearestPollingStationRequest;
import internship.project.election.model.Election;
import internship.project.election.model.function.RegisteredCandidate;
import internship.project.election.model.view.VPollingStation;
import internship.project.election.model.view.VRegisteredVoter;
import internship.project.election.service.domain.AdministrativeDivisionService;
import internship.project.election.service.domain.CandidateService;
import internship.project.election.service.domain.ElectionService;
import internship.project.election.service.domain.PollingStationService;
import internship.project.election.service.domain.VoterService;

@RequestMapping("/api/polling-stations")
@RestController
public class PollingStationController {

    private PollingStationService service;
    private AdministrativeDivisionService administrativeDivisionService;
    private CandidateService candidateService;
    private VoterService voterService;
    private ElectionService electionService;

    public PollingStationController(PollingStationService service,
            AdministrativeDivisionService administrativeDivisionService,
            CandidateService candidateService, VoterService voterService, ElectionService electionService) {
        this.service = service;
        this.administrativeDivisionService = administrativeDivisionService;
        this.candidateService = candidateService;
        this.voterService = voterService;
        this.electionService = electionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPollingStation(
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId,
            @RequestParam(required = false) Integer fokontanyId,
            @PageableDefault(value = Pagination.DEFAULT_SIZE, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("pollingStations",
                this.service.getAllPollingStation(regionId, districtId, communeId, fokontanyId, pageable));
        data.put("regions", this.administrativeDivisionService.getRegionsWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @PostMapping("/nearest")
    public ResponseEntity<ApiResponse> getNearestPollingStations(@RequestBody NearestPollingStationRequest request) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("pollingStations", this.service.getNearestPollingStations(request));
        data.put("elections", this.electionService.getCurrentElections());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/data/{electionId}/{pollingStationId}")
    public ResponseEntity<ApiResponse> getPollingStationData(@PathVariable Integer electionId,
            @PathVariable Integer pollingStationId) {
        HashMap<String, Object> data = new HashMap<>();
        Election election = this.electionService.getElection(electionId).get();
        if (election == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Election not found"));
        }
        VPollingStation pollingStation = this.service.getPollingStationById(pollingStationId).get();
        if (pollingStation == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Polling station not found"));
        }
        List<VRegisteredVoter> voters = this.voterService.getRegisteredVoters(electionId, pollingStationId);
        List<RegisteredCandidate> candidates = this.candidateService.getRegisteredCandidates(electionId,
                pollingStationId);
        election.setCandidates(candidates.size());
        pollingStation.setVoters(voters.size());
        data.put("pollingStation", pollingStation);
        data.put("election", election);
        data.put("voters", voters);
        data.put("candidates", candidates);
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

}
