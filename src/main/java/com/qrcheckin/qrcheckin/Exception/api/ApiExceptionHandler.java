package com.qrcheckin.qrcheckin.Exception.api;

import com.qrcheckin.qrcheckin.Records.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice( basePackages = "com.qrcheckin.qrcheckin.Controllers.api")
public class ApiExceptionHandler {
    @ExceptionHandler(UnauthenticatedApiCallException.class)
    public ResponseEntity<ApiResponse> unauthenticatedApiHandler(UnauthenticatedApiCallException e){

        //log this
        var request = e.getRequest();

        var body = new ApiResponse(ApiResponse.ResponseStatuses.ERROR,"Api key not valid");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ApiFailedException.class)
    public ResponseEntity<ApiResponse> unauthenticatedApiHandler(ApiFailedException e){

        //log this
        var p = e.getCause();

        var body = new ApiResponse(ApiResponse.ResponseStatuses.ERROR,e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public  ResponseEntity<ApiResponse> groupNotFoundHandler(GroupNotFoundException e){

        var body = new ApiResponse(ApiResponse.ResponseStatuses.ERROR,e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse response = new ApiResponse(
                ApiResponse.ResponseStatuses.ERROR,
                "Validation failed",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
