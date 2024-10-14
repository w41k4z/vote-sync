package ceni.system.votesync.service.spec;

public interface TokenService<O> {
    String generateToken(O fromObject, Long BONUS_TIME);

    boolean validateToken(String token);
}
