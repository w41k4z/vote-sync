package internship.project.election.service.impl.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.config.State;
import internship.project.election.model.Election;

public class ElectionSpecification {

    public static Specification<Election> closedElections() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), State.CLOSED);
    }

    public static Specification<Election> currentElection() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), State.PENDING);
    }
}
