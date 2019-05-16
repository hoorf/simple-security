package org.github.ruifengho.simplesecurity.exception;

public class SimpleSecurityException extends RuntimeException {

    public SimpleSecurityException(String message) {
        super(message);
    }

    public SimpleSecurityException(Throwable cause) {
        super(cause);
    }

    public SimpleSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
