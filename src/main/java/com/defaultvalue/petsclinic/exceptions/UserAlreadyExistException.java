package com.defaultvalue.petsclinic.exceptions;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(final String message) {
        super(message);
    }
}
