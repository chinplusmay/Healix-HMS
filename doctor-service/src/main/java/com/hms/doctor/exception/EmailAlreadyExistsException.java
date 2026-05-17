package com.hms.doctor.exception;

/**
 * Thrown when registering a doctor whose email is already on record.
 * Mapped to HTTP 409 Conflict by GlobalExceptionHandler.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
