package com.interview.client.exception;

public class VerificationFailedException extends RuntimeException {

    public VerificationFailedException(String message) {
        super(message);
    }
}