package com.tasksmart.note.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Custom response object for returning data to the client.
 * This object contains a message, status, total records, and data.
 * @param <T> The type of data to be returned.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomResponse<T> {
    private String message;
    private int status;
    private int totalRecords;
    private T data;
}
