package ceni.system.votesync.dto.request.result;

public interface ElectoralResultRequest {

    public Integer getInvalidVotes();

    public Integer getRegistered();

    public Integer getTotalVotes();

    default boolean isValid() {
        return this.getTotalVotes() == this.getRegistered();
    }
}
