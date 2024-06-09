package com.tasksmart.sharedLibrary.exceptions;

import lombok.Getter;

/**
 * Custom RuntimeException subclass representing a resource not found.
 * This exception is thrown when a client request is not found.
 *
 * @author Duy Hoang
 */
@Getter
public class ResourceNotFound extends RuntimeException {
    private int code = 1000;

    /**
     * Constructs a new ResourceConflict exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(int code, String message) {
        super(message);
        this.code = code;
    }
}
