package internship.project.election.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import internship.project.election.model.Role;
import internship.project.election.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void shouldReturnUserWhenFindByIdentifier() {
        // given
        String identifier = "identifier";
        Role userRole = new Role("roleName", 1);
        userRole.setId(1); // OPERATOR ROLE
        User user = new User(
                identifier,
                userRole,
                "name",
                "firstName",
                "contact",
                "{noop}password",
                0, null);
        underTest.save(user);
        // when
        Optional<User> userByIdentifer = underTest.findByIdentifier(identifier);
        // then
        assertThat(userByIdentifer).isPresent();
        underTest.deleteById(userByIdentifer.get().getId());
    }
}
