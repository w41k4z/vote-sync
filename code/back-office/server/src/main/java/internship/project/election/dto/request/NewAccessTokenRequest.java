package internship.project.election.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewAccessTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
