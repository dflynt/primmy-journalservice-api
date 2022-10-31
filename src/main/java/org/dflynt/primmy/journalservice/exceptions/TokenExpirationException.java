package org.dflynt.primmy.journalservice.exceptions;

public class TokenExpirationException extends Exception{
    public TokenExpirationException() {
        super("Token Expired");
    }
}
