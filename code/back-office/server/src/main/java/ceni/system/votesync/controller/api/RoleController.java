package ceni.system.votesync.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.service.domain.RoleService;

import java.util.HashMap;

@RequestMapping("/api/roles")
@RestController
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRoles() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("roles", this.roleService.getAllRoles());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}
