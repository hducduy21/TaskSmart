package com.tasksmart.sharedLibrary.exceptions;

import lombok.Getter;

/**
 * Custom RuntimeException subclass representing a resource conflict.
 * This exception is thrown when a client request is conflict.
 *
 * @author Duy Hoang
 */
@Getter
public class ResourceConflict extends RuntimeException {
    private int code = 1000;

    /**
     * Constructs a new ResourceConflict exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public ResourceConflict(String message) {
        super(message);
    }

    public ResourceConflict(int code, String message) {
        super(message);
        this.code = code;
    }
}