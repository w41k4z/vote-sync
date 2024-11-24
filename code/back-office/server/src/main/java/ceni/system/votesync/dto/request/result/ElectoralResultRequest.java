package ceni.system.votesync.dto.request.result;

public interface ElectoralResultRequest {

    public Integer getInvalidVotes();

    public Integer getVoters();

    public Integer getRegisteredVoters();

    public Integer getTotalVotes();

    default boolean isValid() {
        int totalVotes = this.getTotalVotes();
        return totalVotes == this.getRegisteredVoters() && totalVotes <= this.getVoters();
    }
}
