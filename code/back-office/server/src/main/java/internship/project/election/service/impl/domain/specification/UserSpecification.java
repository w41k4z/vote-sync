package internship.project.election.service.impl.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.User;
import jakarta.persistence.criteria.Predicate;

public class UserSpecification {

    public static Specification<User> getUsersByFilter(String filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.gt(
                    cb.function("CONTAINS", Integer.class,
                            root.get("searchColumn"),
                            cb.literal(filter),
                            cb.literal(1)), // relevance score
                    0);
            return predicate;
        };
    }

    public static Specification<User> getUsersByType(Integer userType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role").get("id"), userType);
    }
}
