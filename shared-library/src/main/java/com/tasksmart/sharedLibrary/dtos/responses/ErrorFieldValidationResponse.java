package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A class representing an error validation response.
 * This class contains information about an error, including status code and error message.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorFieldValidationResponse {
    /** The status code associated with the error. */
    private int statusCode;

    /** The field name that error. */
    private List<FieldValidationError> errors;

    /** The error message error. */
    @Builder.Default
    private String message = "Invalid data entered";

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class FieldValidationError{
        private String name;

        /** The error message error. */
        private String message;
    }
}
