package internship.project.election.controller.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.config.Pagination;
import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.election.ConfigureElectionRequest;
import internship.project.election.dto.request.election.UpdateElectionRequest;
import internship.project.election.model.Election;
import internship.project.election.service.impl.domain.ElectionService;

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

    @GetMapping("/current")
    public ResponseEntity<ApiResponse> getCurrentElection() {
        HashMap<String, Object> data = new HashMap<>();
        List<Election> currentElection = this.service.getCurrentElections();
        String message = currentElection.isEmpty() ? "No current election" : "";
        data.put("election", currentElection);
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
