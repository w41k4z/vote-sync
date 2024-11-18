package ceni.system.votesync.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.service.entity.stat.ElectionStatService;

@RequestMapping("/api/elections/stats")
@RestController
public class ElectionStatController {

    private ElectionStatService electionStatService;

    public ElectionStatController(ElectionStatService electionStatService) {
        this.electionStatService = electionStatService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getVotersStat() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("votersStats", this.electionStatService.getVotersStats());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/fokontany")
    public ResponseEntity<ApiResponse> getFokontanyStat(@RequestParam(required = false) Integer electionTypeId) {
        HashMap<String, Object> data = new HashMap<>();
        if (electionTypeId != null) {
            data.put("stats", this.electionStatService.getAllFokontanyStat(electionTypeId));
        } else {
            data.put("stats", this.electionStatService.getAllGlobalFokontanyStat());
        }
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/commune")
    public ResponseEntity<ApiResponse> getCommunalStat(@RequestParam(required = false) Integer electionTypeId) {
        HashMap<String, Object> data = new HashMap<>();
        if (electionTypeId != null) {
            data.put("stats", this.electionStatService.getAllCommunalStat(electionTypeId));
        } else {
            data.put("stats", this.electionStatService.getAllGlobalCommunalStat());
        }
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/district")
    public ResponseEntity<ApiResponse> getDistrictStat(@RequestParam(required = false) Integer electionTypeId) {
        HashMap<String, Object> data = new HashMap<>();
        if (electionTypeId != null) {
            data.put("stats", this.electionStatService.getAllDistrictStat(electionTypeId));
        } else {
            data.put("stats", this.electionStatService.getAllGlobalDistrictStat());
        }
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/region")
    public ResponseEntity<ApiResponse> getRegionalStat(@RequestParam(required = false) Integer electionTypeId) {
        HashMap<String, Object> data = new HashMap<>();
        if (electionTypeId != null) {
            data.put("stats", this.electionStatService.getAllRegionalStat(electionTypeId));
        } else {
            data.put("stats", this.electionStatService.getAllGlobalRegionalStat());
        }
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
