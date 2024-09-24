package internship.project.election.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.PollingStationService;

@RequestMapping("/api/polling-stations")
@RestController
public class PollingStationController {

    private PollingStationService service;

    public PollingStationController(PollingStationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPollingStation() {
        return ResponseEntity.ok(new ApiResponse(this.service.getAllPollingStation(), null));
    }
}
