package ceni.system.votesync.dto.request.result;

public interface ElectoralResultRequest {

    public Integer getInvalidVotes();

    public Integer getVoters();

    public Integer getTotalVotes();

    default boolean isValid() {
        return this.getTotalVotes() <= this.getVoters();
    }
}
