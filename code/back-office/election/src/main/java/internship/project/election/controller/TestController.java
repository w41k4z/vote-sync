package internship.project.election.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.domain.Role;
import internship.project.election.repository.RoleRepository;

@RequestMapping("/test")
@RestController
public class TestController {

    private RoleRepository roleRepository;

    public TestController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public void index() {
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            System.out.println(role.getName());
        }
        System.out.println(roles.size());
    }
}
