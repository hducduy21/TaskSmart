package com.tasksmart.sharedLibrary.exceptions;

/**
 * Custom RuntimeException subclass representing a resource conflict.
 * This exception is thrown when a client request is conflict.
 *
 * @author Duy Hoang
 */
public class ResourceConflict extends RuntimeException {
    /**
     * Constructs a new ResourceConflict exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public ResourceConflict(String message) {
        super(message);
    }
}