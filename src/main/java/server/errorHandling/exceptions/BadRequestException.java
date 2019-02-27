package server.errorHandling.exceptions;

public class BadRequestException extends Exception {
    String cause;
    public BadRequestException(String cause) {
        this.cause = cause;
    }
}
