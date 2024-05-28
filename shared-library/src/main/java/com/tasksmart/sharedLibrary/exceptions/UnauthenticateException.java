package com.tasksmart.sharedLibrary.exceptions;

public class UnauthenticateException extends RuntimeException {
    /**
     * Constructs a new UnauthenticateException exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public UnauthenticateException(String message) {
        super(message);
    }
}