package ceni.system.votesync.controller;

import java.io.IOException;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalPollingStationResult;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalPollingStationResultRepository;
import ceni.system.votesync.service.entity.result.ProvisionalElectoralResultSpecification;
import ceni.system.votesync.service.pdf.PollingStationResultPdf;

@RequestMapping("/test")
@RestController
public class TestController {

    private PasswordEncoder encoder;
    private ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository;
    private PollingStationResultPdf pollingStationResultPdf;

    public TestController(PasswordEncoder encoder,
            ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository,
            PollingStationResultPdf pollingStationResultPdf) {
        this.encoder = encoder;
        this.provisionalPollingStationResultRepository = provisionalPollingStationResultRepository;
        this.pollingStationResultPdf = pollingStationResultPdf;
    }

    @GetMapping
    public void test() throws IOException {
        Specification<ProvisionalPollingStationResult> spec = ProvisionalElectoralResultSpecification
                .withElectionId(147);
        this.pollingStationResultPdf.generate("", "VOKATRA VONJIMAIKA AMIN'NY FIFIDIANANA BEN'NY TANANA",
                this.provisionalPollingStationResultRepository.findAll(spec));
    }

    @GetMapping("/hash")
    public String hash(@RequestBody String password) {
        return this.encoder.encode("password");
    }
}