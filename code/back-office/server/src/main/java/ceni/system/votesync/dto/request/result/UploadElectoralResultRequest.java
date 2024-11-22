package ceni.system.votesync.dto.request.result;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadElectoralResultRequest implements ElectoralResultRequest {

    @NotNull(message = "Election  id is required")
    private Integer electionId;

    @NotNull(message = "Polling station id is required")
    private Integer pollingStationId;

    @NotBlank(message = "Polling station code is required")
    private String pollingStationCode;

    @NotNull(message = "Null votes is required")
    private Integer nulls;

    @NotNull(message = "Blank votes is required")
    private Integer blanks;

    @NotNull(message = "The number of voters is required")
    private Integer voters;

    private Map<Integer, Integer> candidates;

    @Override
    public Integer getInvalidVotes() {
        return this.nulls + this.blanks;
    }

    @Override
    public Integer getTotalVotes() {
        return this.candidates.values().stream().mapToInt(Integer::intValue).sum() + this.getInvalidVotes();
    }
}
