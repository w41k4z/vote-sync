package ceni.system.votesync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlertBody {

    private Integer alertsCount;
    private String message;
}
