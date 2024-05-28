package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class representing a success response.
 *
 * @Author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SuccessResponse {
    /** The status code associated with the success. */
    private int statusCode = 200;

    /** The success message. */
    private String message;
}
