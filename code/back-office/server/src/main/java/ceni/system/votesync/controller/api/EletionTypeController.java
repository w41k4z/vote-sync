package ceni.system.votesync.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.service.entity.election.ElectionTypeService;

@RestController
@RequestMapping("/api/election-types")
public class EletionTypeController {

    private ElectionTypeService electionTypeService;

    public EletionTypeController(ElectionTypeService electionTypeService) {
        this.electionTypeService = electionTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getElectionTypes() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("electionTypes", this.electionTypeService.getElectionTypes());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}