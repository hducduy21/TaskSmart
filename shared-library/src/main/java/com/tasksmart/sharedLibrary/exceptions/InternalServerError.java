package com.tasksmart.sharedLibrary.exceptions;

/**
 * Custom InternalServerError subclass representing a bad request.
 * This exception is thrown when a client request is invalid or malformed.
 *
 * @author Duy Hoang
 */
public class InternalServerError extends RuntimeException {
    /**
     * Constructs a new InternalServerError exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public InternalServerError(String message) {
        super(message);
    }
}
