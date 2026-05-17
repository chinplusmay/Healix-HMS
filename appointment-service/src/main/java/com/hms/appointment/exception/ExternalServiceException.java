package com.hms.appointment.exception;

/**
 * Thrown when an upstream microservice (patient-service, doctor-service) is
 * unreachable, times out, or returns a non-404 error. Mapped to HTTP 502 Bad Gateway.
 */
public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
