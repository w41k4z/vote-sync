package ceni.system.votesync.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    private PasswordEncoder encoder;

    public TestController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("/hash")
    public String hash(@RequestBody String password) {
        return this.encoder.encode("password");
    }
}