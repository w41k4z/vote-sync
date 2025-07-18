package ceni.system.votesync.controller.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.config.Pagination;
import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.dto.request.NearestPollingStationRequest;
import ceni.system.votesync.dto.request.RegisterVoterRequest;
import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.function.RegisteredCandidate;
import ceni.system.votesync.model.view.VPollingStation;
import ceni.system.votesync.model.view.VRegisteredVoter;
import ceni.system.votesync.service.entity.candidate.CandidateService;
import ceni.system.votesync.service.entity.election.ElectionService;
import ceni.system.votesync.service.entity.location.AdministrativeDivisionService;
import ceni.system.votesync.service.entity.location.PollingStationService;
import ceni.system.votesync.service.entity.voter.VoterService;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

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
        data.put("regions", this.administrativeDivisionService.getRegions());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @PostMapping("/nearest")
    public ResponseEntity<ApiResponse> getNearestPollingStationsAndCurrentElections(
            @RequestBody NearestPollingStationRequest request) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("pollingStations", this.service.getNearestPollingStations(request));
        data.put("elections", this.electionService.getCurrentElections());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<ApiResponse> getPollingStationByCode(@PathVariable String code) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("pollingStation", this.service.getPollingStationByCode(code));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/data/{electionId}/{pollingStationId}")
    public ResponseEntity<ApiResponse> getPollingStationData(@PathVariable Integer electionId,
            @PathVariable Integer pollingStationId) {
        HashMap<String, Object> data = new HashMap<>();
        Election election = this.electionService.getElection(electionId).get();
        if (election == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Election non trouvée"));
        }
        VPollingStation pollingStation = this.service.getPollingStationById(pollingStationId).get();
        if (pollingStation == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Burau de vote non trouvé"));
        }
        List<VRegisteredVoter> voters = this.voterService.getRecordedVoters(electionId, pollingStationId);
        List<RegisteredCandidate> candidates = this.candidateService.getRegisteredCandidates(electionId,
                pollingStationId);
        pollingStation.setCandidates(candidates.size());
        pollingStation.setVoters(voters.size());
        data.put("pollingStation", pollingStation);
        data.put("election", election);
        data.put("voters", voters);
        data.put("candidates", candidates);
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @PostMapping("/register-voters")
    public ResponseEntity<ApiResponse> registerVoters(@RequestBody RegisterVoterRequest[] registration) {
        this.voterService.registerVoters(registration);
        return ResponseEntity.ok(new ApiResponse(null, null));
    }
}
