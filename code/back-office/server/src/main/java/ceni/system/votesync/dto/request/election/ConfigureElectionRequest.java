package ceni.system.votesync.dto.request.election;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfigureElectionRequest {

    @NotNull(message = "Election type id is required")
    private Integer electionTypeId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}
