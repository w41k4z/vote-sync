package ceni.system.votesync.dto.request.result;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/* 
* This request is for correction, not incoherent result, so the number of registered voters is the same as the total votes 
*/
@Data
public class ValidateElectoralResultRequest implements ElectoralResultRequest {

    @NotNull(message = "Result id is required")
    private Integer resultId;

    @NotNull(message = "The number of null votes is required")
    private Integer nulls;

    @NotNull(message = "The number of blank votes is required")
    private Integer blanks;

    @NotNull(message = "The number of male voters under 36 is required")
    private Integer maleUnder36;

    @NotNull(message = "The number of female voters under 36 is required")
    private Integer femaleUnder36;

    @NotNull(message = "The number of male voters 36 and over is required")
    private Integer male36AndOver;

    @NotNull(message = "The number of female voters 36 and over is required")
    private Integer female36AndOver;

    @NotNull(message = "The number of disabled people is required")
    private Integer disabledPeople;

    @NotNull(message = "The number of visually impaired people is required")
    private Integer visuallyImpairedPeople;

    @NotNull(message = "The number of voters is required")
    private Integer voters;

    @NotNull(message = "The votes of each candidate are required")
    private Map<Integer, Integer> candidates;

    @Override
    public Integer getInvalidVotes() {
        return this.blanks + this.nulls;
    }

    @Override
    public Integer getTotalVotes() {
        return this.candidates.values().stream().mapToInt(Integer::intValue).sum() + this.getInvalidVotes();
    }

    @Override
    public Integer getRegisteredVoters() {
        return this.getTotalVotes();
    }

    @Override
    public boolean isValid() {
        int registered = this.male36AndOver + this.female36AndOver + this.maleUnder36 + this.femaleUnder36;
        int totalVotes = this.getTotalVotes();
        return registered == totalVotes && totalVotes <= this.voters;
    }
}
