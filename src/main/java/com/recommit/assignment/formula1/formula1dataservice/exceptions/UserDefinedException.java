package com.recommit.assignment.formula1.formula1dataservice.exceptions;

public class UserDefinedException extends Exception {

    public UserDefinedException(String message) {
        super(message);
    }

    public UserDefinedException(String message, Throwable cause) {
        super(message, cause);
    }
}
