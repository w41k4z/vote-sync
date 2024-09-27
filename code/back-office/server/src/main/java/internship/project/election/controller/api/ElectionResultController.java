package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.repository.result.MunicipalResultRepository;
import internship.project.election.service.impl.domain.result.LegislativeResultService;
import internship.project.election.service.impl.domain.result.LocalResultService;
import internship.project.election.service.impl.domain.result.PresidentialResultService;

@RequestMapping("/api/elections/results")
@RestController
public class ElectionResultController {

    private PresidentialResultService presidentialResultService;
    private LegislativeResultService legislativeResultService;
    private LocalResultService localResultService;

    private MunicipalResultRepository municipalResultRepository;

    public ElectionResultController(PresidentialResultService presidentialResultService,
            LegislativeResultService legislativeResultService, LocalResultService localResultService,
            MunicipalResultRepository municipalResultRepository) {
        this.presidentialResultService = presidentialResultService;
        this.legislativeResultService = legislativeResultService;
        this.localResultService = localResultService;
        this.municipalResultRepository = municipalResultRepository;
    }

    @GetMapping("/local/municipal")
    public ResponseEntity<ApiResponse> localMunicipalResults() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("municipalResults", this.municipalResultRepository.findAll());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }

    @GetMapping("/local/municipal/details")
    public ResponseEntity<ApiResponse> localMunicipalResultDetails() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("municipalResults", this.localResultService.getMunicipalResultDetails());
        return ResponseEntity.ok(new ApiResponse(data, null));
    }
}
