package internship.project.election.service.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.result.ElectoralResult;

public class ElectoralResultSpecification {

    public static <T extends ElectoralResult> Specification<T> withElectionId(Integer electionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("electionId"),
                electionId);
    }
}
