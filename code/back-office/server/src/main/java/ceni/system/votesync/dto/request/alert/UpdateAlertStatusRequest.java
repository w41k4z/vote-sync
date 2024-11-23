package ceni.system.votesync.dto.request.alert;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAlertStatusRequest {

    @NotNull(message = "The alert id is requiered")
    private Integer alertId;

    @NotNull(message = "Status is required")
    private Integer status;
}
