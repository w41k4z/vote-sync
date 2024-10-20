package ceni.system.votesync.dto.request.auth;

import lombok.Data;

@Data
public class LogOutRequest {

    private String refreshToken;
}
