package com.tasksmart.sharedLibrary.configs;

import com.tasksmart.sharedLibrary.dtos.responses.ErrorFieldValidationResponse;
import com.tasksmart.sharedLibrary.dtos.responses.ErrorResponse;
import com.tasksmart.sharedLibrary.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle exceptions that are thrown in the application.
 * This class is annotated with @RestControllerAdvice to handle exceptions thrown by the controllers
 *
 * @author Duy Hoang
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * This method is used to handle the UnauthenticateException.
     *
     * @param unauthenticateException The UnauthenticateException that is thrown
     * @return ErrorResponse
     */
    @ExceptionHandler(UnauthenticateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthenticateException(UnauthenticateException unauthenticateException) {
        return ErrorResponse.builder()
                .message(unauthenticateException.getMessage())
                .statusCode(unauthenticateException.getCode())
                .build();
    }

    /**
     * This method is used to handle the Forbidden.
     *
     * @param forbidden The Forbidden that is thrown
     * @return ErrorResponse
     */
    @ExceptionHandler(Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse forbidden(Forbidden forbidden) {
        return ErrorResponse.builder()
                .message(forbidden.getMessage())
                .statusCode(forbidden.getCode())
                .build();
    }

    /**
     * This method is used to handle the ResourceNotFound.
     *
     * @param resourceNotFound The ResourceNotFound that is thrown
     * @return ErrorResponse
     */
    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFound(ResourceNotFound resourceNotFound) {
        return ErrorResponse.builder()
                .message(resourceNotFound.getMessage())
                .statusCode(resourceNotFound.getCode())
                .build();
    }

    /**
     * This method is used to handle the ResourceConflict.
     *
     * @param resourceConflict The ResourceConflict that is thrown
     * @return ErrorResponse
     */
    @ExceptionHandler(ResourceConflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse resourceConflict(ResourceConflict resourceConflict) {
        return ErrorResponse.builder()
                .message(resourceConflict.getMessage())
                .statusCode(resourceConflict.getCode())
                .build();
    }

    /**
     * This method is used to handle the BadRequest.
     *
     * @param badRequest The BadRequest that is thrown
     * @return ErrorResponse
     */
    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(BadRequest badRequest) {
        return ErrorResponse.builder()
                .message(badRequest.getMessage())
                .statusCode(badRequest.getCode())
                .build();
    }

    /**
     * This method is used to handle the InternalServerError.
     *
     * @param internalServerError The InternalServerError that is thrown
     * @return ErrorResponse
     */
    @ExceptionHandler(InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerError(InternalServerError internalServerError) {
        return ErrorResponse.builder()
                .message(internalServerError.getMessage())
                .statusCode(internalServerError.getCode())
                .build();
    }

    /**
     * This method is used to handle the MethodArgumentNotValidException.
     *
     * @param methodArgumentNotValidException The MethodArgumentNotValidException that is thrown
     * @return ErrorResponse
     */
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

        String message = errors.isEmpty() ? "" : errors.get(errors.size() - 1).getMessage();

        return ErrorFieldValidationResponse.builder()
                .errors(errors)
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

    }
}
