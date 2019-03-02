package server.errorHandling.exceptions;

public class CounterAlreadyExistsException extends Exception {

    String name;

    public CounterAlreadyExistsException(String name) {
        this.name = name;
    }

    public String getMessage() {
        return "Can't create Counter because the Counter \"" + name + "\" already exists";
    }
}
