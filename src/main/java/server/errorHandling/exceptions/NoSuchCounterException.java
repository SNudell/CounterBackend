package server.errorHandling.exceptions;

public class NoSuchCounterException extends Exception {
    public String searchedName;

    public NoSuchCounterException (String searchedName) {
        this.searchedName = searchedName;
    }
}
