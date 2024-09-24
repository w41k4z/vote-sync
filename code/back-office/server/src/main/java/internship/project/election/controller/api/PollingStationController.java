package internship.project.election.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
	HashMap<String, Object> data = new HashMap<>();
	data.put("pollingStations", this.service.getAllPollingStation());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
