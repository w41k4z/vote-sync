package ceni.system.votesync.exception;

public class ElectionTypeNotFoundException extends RuntimeException {
    public ElectionTypeNotFoundException(String message) {
        super(message);
    }

    public ElectionTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
