package ceni.system.votesync.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.service.domain.AdministrativeDivisionService;

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

    @GetMapping("/districts")
    public ResponseEntity<ApiResponse> getDistricts() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getDistrictsWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/districts/by-region")
    public ResponseEntity<ApiResponse> getDistrictsByRegionId(@RequestParam Integer upperDivisionId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions",
                this.service.getDistrictsByRegionIdWithoutGeoJson(upperDivisionId));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/municipality-districts")
    public ResponseEntity<ApiResponse> getMunicipalityDistricts() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getMunicipalityDistrictsWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/municipality-districts/by-region")
    public ResponseEntity<ApiResponse> getMunicipalityDistrictsByRegionId(@RequestParam Integer upperDivisionId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions",
                this.service.getMunicipalityDistrictsByRegionIdWithoutGeoJson(upperDivisionId));
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
        payload.put("administrativeDivisions",
                this.service.getCommunesByDistrictIdWithoutGeoJson(upperDivisionId));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/municipalities")
    public ResponseEntity<ApiResponse> getMunicipalities() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions", this.service.getMunicipalitiesWithoutGeoJson());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/municipalities/by-district")
    public ResponseEntity<ApiResponse> getMunicipalitiesByDistrictId(@RequestParam Integer upperDivisionId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("administrativeDivisions",
                this.service.getMunicipalitiesByDistrictIdWithoutGeoJson(upperDivisionId));
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
        payload.put("administrativeDivisions",
                this.service.getFokontanyByCommuneIdWithoutGeoJson(upperDivisionId));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}
