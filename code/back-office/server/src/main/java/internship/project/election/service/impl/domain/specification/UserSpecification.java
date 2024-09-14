package internship.project.election.service.impl.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.User;

public class UserSpecification {

    public static Specification<User> getActiveUsers() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), 0);
    }
}
