package com.hms.appointment.exception;

/**
 * Thrown when an appointment refers to a patientId or doctorId that does not exist
 * in the upstream service. The bad ID is in the *request*, so this maps to
 * HTTP 400 Bad Request rather than 404 Not Found (which would be misleading —
 * the appointment itself doesn't exist yet, the *referenced entity* doesn't).
 */
public class InvalidReferenceException extends RuntimeException {
    public InvalidReferenceException(String message) {
        super(message);
    }
}
