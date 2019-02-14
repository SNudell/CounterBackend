package server.models;

// handels mapping of exceptions to error models

import server.errorHandling.exceptions.NoSuchCounterException;

public class ErrorMessageMapper {
    public static String getMessageFor(Exception e) {
        if (e instanceof NoSuchCounterException) {
            NoSuchCounterException nSCE = (NoSuchCounterException) e;
            return "No such counter: " + nSCE.searchedName;
        } else {
            return "Unknown error occurred";
        }
    }
}
