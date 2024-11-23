package ceni.system.votesync.controller.api;

import java.util.HashMap;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.config.Pagination;
import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.dto.request.alert.UpdateAlertStatusRequest;
import ceni.system.votesync.service.entity.alert.AlertService;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAlerts(
            @RequestParam(required = false) Integer electionId,
            @RequestParam(required = false) Integer regionId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer communeId,
            @RequestParam(required = false) Integer fokontanyId,
            @PageableDefault(value = Pagination.DEFAULT_SIZE, page = Pagination.DEFAULT_PAGE) Pageable pageable) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("alerts",
                this.alertService.getCurrentAlerts(electionId, regionId, districtId, communeId, fokontanyId, pageable));
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @PutMapping("/update-status")
    public ResponseEntity<ApiResponse> updateAlertStatus(@RequestBody UpdateAlertStatusRequest request) {
        this.alertService.updateAlertStatus(request);
        return ResponseEntity.ok(new ApiResponse(null, "Alert status updated successfully"));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countAlerts() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("alertCounts", this.alertService.countCurrentAlerts());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
