package com.tasksmart.sharedLibrary.exceptions;

/**
 * Custom RuntimeException subclass representing a resource not found.
 * This exception is thrown when a client request is not found.
 *
 * @author Duy Hoang
 */
public class ResourceNotFound extends RuntimeException {
    /**
     * Constructs a new ResourceConflict exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public ResourceNotFound(String message) {
        super(message);
    }
}
