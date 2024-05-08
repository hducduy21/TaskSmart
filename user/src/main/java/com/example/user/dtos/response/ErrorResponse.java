package com.example.user.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class representing an error response.
 * This class contains information about an error, including status code and error message.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    /** The status code associated with the error. */
    private int statusCode;

    /** The error message. */
    private String message;
}
