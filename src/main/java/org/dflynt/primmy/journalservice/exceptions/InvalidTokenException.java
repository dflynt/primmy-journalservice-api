package org.dflynt.primmy.journalservice.exceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException() {
        super("Invalid token");
    }
}
