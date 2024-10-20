package ceni.system.votesync.service.entity.election;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.config.State;
import ceni.system.votesync.model.entity.Election;

public final class ElectionSpecification {

    public static Specification<Election> closedElections() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), State.CLOSED);
    }

    public static Specification<Election> currentElection() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), State.PENDING);
    }
}
