package com.example.user.configs;

import com.example.user.dtos.response.ErrorFieldValidationResponse;
import com.example.user.dtos.response.ErrorResponse;
import com.example.user.exceptions.BadRequest;
import com.example.user.exceptions.ResourceConflict;
import com.example.user.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFound(ResourceNotFound resourceNotFound) {
        return ErrorResponse.builder()
                .message(resourceNotFound.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(ResourceConflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse resourceConflict(ResourceConflict resourceConflict) {
        return ErrorResponse.builder()
                .message(resourceConflict.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(BadRequest badRequest) {
        return ErrorResponse.builder()
                .message(badRequest.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorFieldValidationResponse handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<ErrorFieldValidationResponse.FieldValidationError> errors = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(ErrorFieldValidationResponse.FieldValidationError.builder()
                    .name(fieldName)
                    .message(errorMessage)
                    .build());
        });

        String message = errors.getLast().getMessage();

        return ErrorFieldValidationResponse.builder()
                .errors(errors)
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

    }
}
