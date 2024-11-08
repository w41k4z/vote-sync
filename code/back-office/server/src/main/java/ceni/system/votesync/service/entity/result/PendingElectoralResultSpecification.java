package ceni.system.votesync.service.entity.result;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.view.election.result.PendingElectoralResult;

public class PendingElectoralResultSpecification {

    public static Specification<PendingElectoralResult> withUserIdentifier(String userIdentifier) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("operatorIdentifier"),
                userIdentifier);
    }
}
