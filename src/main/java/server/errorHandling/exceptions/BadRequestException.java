package server.errorHandling.exceptions;

@SuppressWarnings("unused")
public class BadRequestException extends Exception {

    private String cause;

    public BadRequestException(String cause) {
        this.cause = cause;
    }
}
