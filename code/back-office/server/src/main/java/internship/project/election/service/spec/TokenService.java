package internship.project.election.service.spec;

public interface TokenService<O> {
    String generateToken(O fromObject);

    boolean validateToken(String token);
}
