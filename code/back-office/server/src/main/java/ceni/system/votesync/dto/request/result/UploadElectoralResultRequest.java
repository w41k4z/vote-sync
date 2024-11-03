package ceni.system.votesync.dto.request.result;

import java.util.Map;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadElectoralResultRequest implements ElectoralResultRequest {

    private Integer electionId;
    private Integer pollingStationId;
    private Integer nulls;
    private Integer blanks;
    private Integer registered;
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
