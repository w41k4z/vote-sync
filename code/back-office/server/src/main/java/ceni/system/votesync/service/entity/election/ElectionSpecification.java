package ceni.system.votesync.service.entity.election;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.model.entity.election.Election;

public final class ElectionSpecification {

    public static Specification<Election> closedElections() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), Status.CLOSED);
    }

    public static Specification<Election> currentElection() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), Status.PENDING);
    }
}
