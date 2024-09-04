package internship.project.election.service.spec.util;

public abstract class AbstractPasswordHashing {

    protected abstract String getIdForEncode();

    protected abstract String hash(String password);

    public String getFormattedPassword(String password) {
        return "{" + this.getIdForEncode() + "}" + this.hash(password);
    }
}
