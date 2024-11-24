package ceni.system.votesync.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.service.ResultPdfGeneratorService;

@RequestMapping("/test")
@RestController
public class TestController {

    private PasswordEncoder encoder;
    private ResultPdfGeneratorService resultPdfGeneratorService;

    public TestController(PasswordEncoder encoder, ResultPdfGeneratorService resultPdfGeneratorService) {
        this.encoder = encoder;
        this.resultPdfGeneratorService = resultPdfGeneratorService;
    }

    @GetMapping
    public void test() {
        this.resultPdfGeneratorService.test();
    }

    @GetMapping("/hash")
    public String hash(@RequestBody String password) {
        return this.encoder.encode("password");
    }
}