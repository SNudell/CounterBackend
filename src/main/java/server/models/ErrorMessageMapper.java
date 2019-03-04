package server.models;

// handels mapping of exceptions to error models

import server.errorHandling.exceptions.BadNameException;
import server.errorHandling.exceptions.BadRequestException;
import server.errorHandling.exceptions.CounterAlreadyExistsException;
import server.errorHandling.exceptions.NoSuchCounterException;

public class ErrorMessageMapper {
    public static String getMessageFor(Exception e) {
        System.out.println("mapping error");
        if (e instanceof NoSuchCounterException) {
            NoSuchCounterException nSCE = (NoSuchCounterException) e;
            return "No such counter: " + nSCE.searchedName;
        } else if (e instanceof BadRequestException) {
            BadRequestException bRE = (BadRequestException) e;
            return "Cause: " + bRE.getCause();
        } else if (e instanceof CounterAlreadyExistsException) {
            CounterAlreadyExistsException cAEE = (CounterAlreadyExistsException) e;
            return cAEE.getMessage();
        } else if (e instanceof BadNameException) {
            return "Counter names may only contain lowercase, uppercase and numbers";
        }
        else {
            return "Unknown error occurred";
        }
    }
}
