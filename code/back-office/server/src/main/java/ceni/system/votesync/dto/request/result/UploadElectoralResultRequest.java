package ceni.system.votesync.dto.request.result;

import java.util.Map;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadElectoralResultRequest {

    private Integer electionId;
    private Integer pollingStationId;
    private Integer nulls;
    private Integer blanks;
    private Integer registered;
    private Map<Integer, Integer> candidates;

    public int getInvalidVotes() {
        return this.nulls + this.blanks;
    }

    public int getTotalVotes() {
        return this.candidates.values().stream().mapToInt(Integer::intValue).sum() + this.getInvalidVotes();
    }

    public boolean isValid() {
        return this.getTotalVotes() == this.registered;
    }
}
