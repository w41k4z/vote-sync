package ceni.system.votesync.service.entity.result;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.base.view.result.ElectoralResult;
import ceni.system.votesync.model.entity.election.result.ResultImage;

public class ElectoralResultSpecification {

    public static <T extends ElectoralResult> Specification<T> withElectionId(Integer electionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("electionId"),
                electionId);
    }

    public static Specification<ResultImage> imagesWithResultId(Integer resultId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("resultId"),
                resultId);
    }
}
