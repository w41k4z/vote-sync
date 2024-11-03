package ceni.system.votesync.exception;

public class ElectoralResultNotFoundException extends RuntimeException {
    public ElectoralResultNotFoundException(String message) {
        super(message);
    }

    public ElectoralResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
