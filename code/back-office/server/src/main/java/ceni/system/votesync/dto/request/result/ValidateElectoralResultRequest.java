package ceni.system.votesync.dto.request.result;

import java.util.Map;

import lombok.Data;

@Data
public class ValidateElectoralResultRequest implements ElectoralResultRequest {

    private Integer resultId;
    private Integer blankVotes;
    private Integer nullVotes;
    private Integer registeredVoters;
    private Map<Integer, Integer> resultDetails;

    @Override
    public Integer getRegistered() {
        return this.registeredVoters;
    }

    @Override
    public Integer getInvalidVotes() {
        return this.blankVotes + this.nullVotes;
    }

    @Override
    public Integer getTotalVotes() {
        return this.resultDetails.values().stream().mapToInt(Integer::intValue).sum() + this.getInvalidVotes();
    }

}
