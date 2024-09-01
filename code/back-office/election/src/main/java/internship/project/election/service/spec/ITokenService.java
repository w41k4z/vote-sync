package internship.project.election.service.spec;

public interface ITokenService<O> {
    String generateToken(O fromObject);

    boolean validateToken(String token);
}
