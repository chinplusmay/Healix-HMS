package com.hms.appointment.exception;

/**
 * Thrown when a caller tries to move an appointment to a status it cannot legally
 * reach (e.g. CANCELLED -> COMPLETED). Mapped to HTTP 409 Conflict.
 */
public class IllegalStatusTransitionException extends RuntimeException {
    public IllegalStatusTransitionException(String message) {
        super(message);
    }
}
