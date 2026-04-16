package com.sicklecare.api.exceptions;

public class InvalidCodeException extends RuntimeException {

    public InvalidCodeException(String message) {
        super(message);
    }

}
