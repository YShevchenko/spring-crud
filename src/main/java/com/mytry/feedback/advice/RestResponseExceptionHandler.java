package com.mytry.feedback.advice;

import com.mytry.feedback.dto.FieldValidationExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseExceptionHandler {
    private static final String VALIDATION_FAILED_MESSAGE = "Validation failed";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldValidationExceptionResponse> invalidInput(MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        FieldValidationExceptionResponse response = new FieldValidationExceptionResponse(VALIDATION_FAILED_MESSAGE, fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}