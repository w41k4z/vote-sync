package ceni.system.votesync.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterVoterRequest {

    @NotNull(message = "The voter id is required")
    private Integer id;

    @NotNull(message = "The election id is required")
    private Integer electionId;

    @NotNull(message = "The polling station id is required")
    private Integer pollingStationId;

    @NotNull(message = "The registration date is required")
    private LocalDateTime registrationDate;
}
