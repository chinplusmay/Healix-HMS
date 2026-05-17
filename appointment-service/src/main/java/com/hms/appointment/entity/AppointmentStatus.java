package com.hms.appointment.entity;

/**
 * Lifecycle states an appointment can move through.
 *
 *   SCHEDULED -> COMPLETED   (doctor saw the patient)
 *   SCHEDULED -> CANCELLED   (either party cancelled)
 *
 * Once an appointment is COMPLETED or CANCELLED it is terminal — it cannot
 * be moved back to SCHEDULED. Enforced in AppointmentService.updateStatus().
 */
public enum AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}
