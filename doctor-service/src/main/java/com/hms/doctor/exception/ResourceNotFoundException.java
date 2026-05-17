package com.hms.doctor.exception;

/**
 * Thrown when a domain entity (typically a Doctor by id) cannot be found.
 * The controller layer translates this to HTTP 404 via GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
