package ceni.system.votesync.exception;

public class PollingStationNotFoundException extends RuntimeException {

    public PollingStationNotFoundException(String message) {
        super(message);
    }

    public PollingStationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
