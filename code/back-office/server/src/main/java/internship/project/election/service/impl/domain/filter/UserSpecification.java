package internship.project.election.service.impl.domain.filter;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.domain.User;

public class UserSpecification {

    public static Specification<User> getActiveUsers() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), 0);
    }
}
