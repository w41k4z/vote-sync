package internship.project.election.service.impl.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.User;
import jakarta.persistence.criteria.Predicate;

public final class UserSpecification {

    public static Specification<User> filterUsers(String filter, Integer userType) {
        Specification<User> spec = Specification.where(null);
        if (filter != null && !filter.isEmpty()) {
            spec = spec.and(getUsersByFilter(filter));
        }
        if (userType != null) {
            spec = spec.and(getUsersByType(userType));
        }
        return spec;
    }

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
