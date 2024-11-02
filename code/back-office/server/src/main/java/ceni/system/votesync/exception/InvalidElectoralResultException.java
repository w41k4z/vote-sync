package ceni.system.votesync.exception;

public class InvalidElectoralResultException extends RuntimeException {
    public InvalidElectoralResultException(String message) {
        super(message);
    }

    public InvalidElectoralResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
