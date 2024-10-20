package ceni.system.votesync.service.entity.user;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.entity.User;
import jakarta.persistence.criteria.Predicate;

public final class UserSpecification {

    public static Specification<User> filterUsers(String filter, Integer userType) {
        Specification<User> spec = Specification.where(null);
        if (filter != null && !filter.isEmpty()) {
            spec = spec.and(withInformation(filter));
        }
        if (userType != null) {
            spec = spec.and(withType(userType));
        }
        return spec;
    }

    public static Specification<User> withInformation(String information) {
        return (root, query, cb) -> {
            Predicate predicate = cb.gt(
                    cb.function("CONTAINS", Integer.class,
                            root.get("searchColumn"),
                            cb.literal(
                                    information),
                            cb.literal(1)), // relevance score
                    0);
            return predicate;
        };
    }

    public static Specification<User> withType(Integer userType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role").get("id"), userType);
    }
}
