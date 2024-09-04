package internship.project.election.service.spec;

public interface AuthService<RESP> {

    public RESP logIn(String username, String password);

    public void logOut(String token);
}