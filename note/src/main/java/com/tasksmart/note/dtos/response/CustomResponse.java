package com.tasksmart.note.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
