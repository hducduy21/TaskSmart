package com.tasksmart.sharedLibrary.exceptions;

import lombok.Getter;

/**
 * Custom Forbidden subclass representing a bad request.
 * This exception is thrown when a client request is invalid or malformed.
 *
 * @author Duy Hoang
 */
@Getter
public class Forbidden extends RuntimeException {
    private int code= 1000;
    /**
     * Constructs a new Forbidden exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public Forbidden(String message) {
        super(message);
    }

    public Forbidden(int code, String message) {
        super(message);
        this.code = code;
    }
}
