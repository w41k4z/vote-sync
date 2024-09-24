package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.AdministrativeDivisionService;

@RestController
@RequestMapping("/api/administrative-divisions")
public class AdministrativeDivisionController {

    private AdministrativeDivisionService service;

    public AdministrativeDivisionController(AdministrativeDivisionService service) {
        this.service = service;
    }

    @GetMapping("/regions")
    public ResponseEntity<ApiResponse> getRegions() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getRegionsWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/regions/stats")
    public ResponseEntity<ApiResponse> getRegionsStat() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisionStats", this.service.getRegionsStat());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/districts")
    public ResponseEntity<ApiResponse> getDistricts() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getDistrictsWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/districts/by-region")
    public ResponseEntity<ApiResponse> getDistrictsByRegionId(@RequestParam Integer upperDivisionId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getDistrictsByRegionId(upperDivisionId));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/districts/stats")
    public ResponseEntity<ApiResponse> getDistrictsStat() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisionStats", this.service.getDistrictsStat());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/communes")
    public ResponseEntity<ApiResponse> getCommunes() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getCommunesWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/communes/by-district")
    public ResponseEntity<ApiResponse> getCommunesByDistrictId(@RequestParam Integer upperDivisionId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getCommunesByDistrictId(upperDivisionId));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/communes/stats")
    public ResponseEntity<ApiResponse> getCommunesStat() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisionStats", this.service.getCommunesStat());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ApiResponse> getFokontany() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getFokontanyWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/fokontany/by-commune")
    public ResponseEntity<ApiResponse> getFokontanyByCommuneId(@RequestParam Integer upperDivisionId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getFokontanyByCommuneId(upperDivisionId));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/fokontany/stats")
    public ResponseEntity<ApiResponse> getFokontanyStat() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisionStats", this.service.getFokontanyStat());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}
