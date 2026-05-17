package com.hms.doctor.entity;

/**
 * Doctor's current duty/availability status.
 *
 * AVAILABLE   - currently accepting appointments
 * UNAVAILABLE - temporarily unavailable (e.g. with a patient, in surgery)
 * ON_LEAVE    - on extended leave (vacation, sick leave, sabbatical)
 */
public enum Availability {
    AVAILABLE,
    UNAVAILABLE,
    ON_LEAVE
}
