package com.example.rent_apartment.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ValidationErrorDetails>> handleBindException(BindException ex) {
        List<ValidationErrorDetails> errorDetailsList = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            ValidationErrorDetails errorDetails = new ValidationErrorDetails(error.getField(), error.getDefaultMessage());
            errorDetailsList.add(errorDetails);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetailsList);
    }

    //HttpClientErrorException$BadRequest
}
