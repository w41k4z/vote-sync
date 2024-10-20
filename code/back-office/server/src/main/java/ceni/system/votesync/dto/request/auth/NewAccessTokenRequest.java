package ceni.system.votesync.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewAccessTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
