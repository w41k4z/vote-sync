package ceni.system.votesync.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.service.pdf.ElectoralResultPdfGenerator;

@RequestMapping("/test")
@RestController
public class TestController {

    private PasswordEncoder encoder;
    private ElectoralResultPdfGenerator electoralResultPdfGenerator;

    public TestController(PasswordEncoder encoder, ElectoralResultPdfGenerator electoralResultPdfGenerator) {
        this.encoder = encoder;
        this.electoralResultPdfGenerator = electoralResultPdfGenerator;
    }

    @GetMapping
    public ResponseEntity<byte[]> globalResults() throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resultat.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(this.electoralResultPdfGenerator.getGlobalResultPdf(101).toByteArray());
    }

    @GetMapping("/hash")
    public String hash(@RequestBody String password) {
        return this.encoder.encode("password");
    }
}