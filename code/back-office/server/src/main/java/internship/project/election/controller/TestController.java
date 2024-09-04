package internship.project.election.controller;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    private PasswordEncoder encoder;

    @SuppressWarnings("deprecation")
    public TestController() {
        this.encoder = NoOpPasswordEncoder.getInstance();
    }

    @GetMapping
    public void index() {
        System.out.println(this.encoder.encode("test"));
    }
}
