package ceni.system.votesync.exception;

public class WrongElectionTypeException extends RuntimeException {
    public WrongElectionTypeException(String message) {
        super(message);
    }

    public WrongElectionTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
