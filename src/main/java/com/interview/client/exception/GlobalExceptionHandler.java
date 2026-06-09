package com.interview.client.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VerificationFailedException.class)
    public ResponseEntity<String> handleVerificationFailure(VerificationFailedException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
