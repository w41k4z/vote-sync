package internship.project.election.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String identifier;
    private String password;
}
