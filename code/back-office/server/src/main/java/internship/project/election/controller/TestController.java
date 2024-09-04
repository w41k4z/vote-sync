package internship.project.election.controller;

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

    @GetMapping
    public String hash(@RequestBody String password) {
        return this.encoder.encode("password");
    }

    @GetMapping("/hash")
    public boolean test() {
        String test = this.encoder.encode("admin");
        System.out.println("\n" + test + "\n");
        return this.encoder.matches(
                "admin",
                test);
    }
}