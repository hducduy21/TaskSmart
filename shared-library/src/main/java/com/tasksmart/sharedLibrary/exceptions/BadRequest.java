package com.tasksmart.sharedLibrary.exceptions;

/**
 * Custom RuntimeException subclass representing a bad request.
 * This exception is thrown when a client request is invalid or malformed.
 *
 * @author Duy Hoang
 */
public class BadRequest extends RuntimeException {
    /**
     * Constructs a new BadRequest exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public BadRequest(String message) {
        super(message);
    }
}
