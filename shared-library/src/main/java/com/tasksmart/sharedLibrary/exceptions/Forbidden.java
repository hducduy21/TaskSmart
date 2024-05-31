package com.tasksmart.sharedLibrary.exceptions;

/**
 * Custom Forbidden subclass representing a bad request.
 * This exception is thrown when a client request is invalid or malformed.
 *
 * @author Duy Hoang
 */
public class Forbidden extends RuntimeException {
    /**
     * Constructs a new Forbidden exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public Forbidden(String message) {
        super(message);
    }
}
