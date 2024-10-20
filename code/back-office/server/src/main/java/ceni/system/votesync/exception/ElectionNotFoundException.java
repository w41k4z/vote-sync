package ceni.system.votesync.exception;

public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(String message) {
        super(message);
    }

    public ElectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
