package ceni.system.votesync.service.entity.result;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.view.result.PendingElectoralResult;
import jakarta.persistence.criteria.Predicate;

public class PendingElectoralResultSpecification {

    public static Specification<PendingElectoralResult> withElectionIdAndUserIdentifier(Integer electionId,
            String userIdentifier) {
        return (root, query, criteriaBuilder) -> {
            Predicate electionIdPredicate = criteriaBuilder.equal(root.get("electionId"), electionId);
            Predicate userIdentifierPredicate = criteriaBuilder.equal(root.get("operatorIdentifier"), userIdentifier);
            return criteriaBuilder.and(electionIdPredicate, userIdentifierPredicate);
        };
    }
}
