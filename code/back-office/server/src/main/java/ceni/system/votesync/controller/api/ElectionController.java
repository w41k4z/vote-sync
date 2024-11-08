package ceni.system.votesync.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.config.Pagination;
import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.dto.request.election.ConfigureElectionRequest;
import ceni.system.votesync.dto.request.election.UpdateElectionRequest;
import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.view.election.VElection;
import ceni.system.votesync.service.entity.election.ElectionService;

@RequestMapping("/api/elections")
@RestController
public class ElectionController {

    private ElectionService service;

    public ElectionController(ElectionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> configureElection(@RequestBody ConfigureElectionRequest request) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("election", this.service.configureElection(request));
        return ResponseEntity.ok(new ApiResponse(data, "Election configured successfully"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateElection(@RequestBody UpdateElectionRequest request) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("election", this.service.updateElection(request));
        return ResponseEntity.ok(new ApiResponse(data, "Election updated successfully"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteElection(Integer electionId) {
        this.service.deleteElection(electionId);
        return ResponseEntity.ok(new ApiResponse(null, "Election deleted successfully"));
    }

    @GetMapping("/{electionId}")
    public ResponseEntity<ApiResponse> getElection(@PathVariable Integer electionId) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Election> election = this.service.getElection(electionId);
        data.put("election", election.get());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/current")
    public ResponseEntity<ApiResponse> getCurrentElections() {
        HashMap<String, Object> data = new HashMap<>();
        List<VElection> currentElections = this.service.getCurrentElections();
        String message = currentElections.isEmpty() ? "No current election" : "";
        data.put("elections", currentElections);
        return ResponseEntity.ok(new ApiResponse(data, message));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse> getElectionsHistory(
            @PageableDefault(value = Pagination.DEFAULT_SIZE, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("elections", this.service.getElectionsHistory(pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
