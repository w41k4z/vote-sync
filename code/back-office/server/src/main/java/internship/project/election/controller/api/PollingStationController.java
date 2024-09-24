package internship.project.election.controller.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import internship.project.election.config.Pagination;
import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.AdministrativeDivisionService;
import internship.project.election.service.impl.domain.PollingStationService;

@RequestMapping("/api/polling-stations")
@RestController
public class PollingStationController {

    private PollingStationService service;
    private AdministrativeDivisionService administrativeDivisionService;

    public PollingStationController(PollingStationService service,
            AdministrativeDivisionService administrativeDivisionService) {
        this.service = service;
        this.administrativeDivisionService = administrativeDivisionService;
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
}
