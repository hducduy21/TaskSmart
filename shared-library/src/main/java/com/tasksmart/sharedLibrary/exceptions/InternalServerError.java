package com.tasksmart.sharedLibrary.exceptions;

import lombok.Getter;

/**
 * Custom InternalServerError subclass representing a bad request.
 * This exception is thrown when a client request is invalid or malformed.
 *
 * @author Duy Hoang
 */
@Getter
public class InternalServerError extends RuntimeException {
    private int code = 1000;
    /**
     * Constructs a new InternalServerError exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public InternalServerError(String message) {
        super(message);
    }

    public InternalServerError(int code, String message) {
        super(message);
        this.code = code;
    }
}
