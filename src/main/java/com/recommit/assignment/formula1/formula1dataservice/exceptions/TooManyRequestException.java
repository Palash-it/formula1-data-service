package com.recommit.assignment.formula1.formula1dataservice.exceptions;

public class TooManyRequestException extends Exception {
    public TooManyRequestException(String message) {
        super(message);
    }

    public TooManyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
