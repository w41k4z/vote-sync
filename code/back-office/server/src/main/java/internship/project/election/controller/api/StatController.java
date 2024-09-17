package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.StatService;

@RestController
@RequestMapping("/api/stats")
public class StatController {

    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/regions")
    public ResponseEntity<ApiResponse> getRegionsStat() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("regions", this.statService.getRegionsStat());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}
