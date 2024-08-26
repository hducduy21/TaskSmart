package com.tasksmart.sharedLibrary.exceptions;

import lombok.Getter;

/**
 * Custom RuntimeException subclass representing a bad request.
 * This exception is thrown when a client request is invalid or malformed.
 *
 * @author Duy Hoang
 */
@Getter
public class BadRequest extends RuntimeException {
    private int code = 1000;
    /**
     * Constructs a new BadRequest exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public BadRequest(String message) {
        super(message);
    }

    public BadRequest(int code, String message) {
        super(message);
        this.code = code;
    }
}
