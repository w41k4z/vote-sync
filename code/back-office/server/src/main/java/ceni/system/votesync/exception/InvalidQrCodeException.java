package ceni.system.votesync.exception;

public class InvalidQrCodeException extends RuntimeException {

    public InvalidQrCodeException(String message) {
        super(message);
    }

    public InvalidQrCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
