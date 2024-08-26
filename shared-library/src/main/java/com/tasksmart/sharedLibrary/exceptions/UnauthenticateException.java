package com.tasksmart.sharedLibrary.exceptions;

import lombok.Getter;

@Getter
public class UnauthenticateException extends RuntimeException {
    private int code = 1000;
    /**
     * Constructs a new UnauthenticateException exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public UnauthenticateException(String message) {
        super(message);
    }

    public UnauthenticateException(int code, String message) {
        super(message);
        this.code = code;
    }
}