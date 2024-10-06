package internship.project.election.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NearestPollingStationRequest {

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    private Double range = 250.;
}
