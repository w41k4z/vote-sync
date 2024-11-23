package ceni.system.votesync.dto.request.result;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvalidateElectoralResultRequest {

    @NotNull(message = "Election id is required")
    private Integer electionId;

    @NotNull(message = "Polling station id is required")
    private Integer pollingStationId;
}
