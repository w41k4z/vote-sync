package internship.project.election.service.impl.domain.filter;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.config.Authority;
import internship.project.election.domain.User;

public class UserFilter {

    public static Specification<User> getActiveUsers() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), 0);
    }

    public static Specification<User> getNonAdminUsers() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("role").get("name"),
                Authority.ADMIN.toString());
    }
}
