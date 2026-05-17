package com.hms.appointment.exception;

/**
 * Thrown when the appointment itself cannot be found by id.
 * Mapped to HTTP 404 by GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
