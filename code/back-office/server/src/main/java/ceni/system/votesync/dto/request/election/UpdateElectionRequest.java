package ceni.system.votesync.dto.request.election;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateElectionRequest {

    @NotNull(message = "Id is required")
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}
