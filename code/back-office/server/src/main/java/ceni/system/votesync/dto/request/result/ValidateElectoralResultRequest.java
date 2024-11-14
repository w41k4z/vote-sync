package ceni.system.votesync.dto.request.result;

import java.util.Map;

import lombok.Data;

@Data
public class ValidateElectoralResultRequest implements ElectoralResultRequest {

    private Integer resultId;
    private Integer nulls;
    private Integer blanks;
    private Integer registered;
    private Map<Integer, Integer> candidates;

    @Override
    public Integer getRegistered() {
        return this.registered;
    }

    @Override
    public Integer getInvalidVotes() {
        return this.blanks + this.nulls;
    }

    @Override
    public Integer getTotalVotes() {
        return this.candidates.values().stream().mapToInt(Integer::intValue).sum() + this.getInvalidVotes();
    }

}
