package com.hms.appointment.dto;

import com.hms.appointment.entity.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "patientId is required")
    @Positive(message = "patientId must be a positive number")
    private Long patientId;

    @NotNull(message = "doctorId is required")
    @Positive(message = "doctorId must be a positive number")
    private Long doctorId;

    @NotNull(message = "appointmentDate is required (ISO-8601 instant, e.g. 2026-06-01T09:30:00Z)")
    @Future(message = "appointmentDate must be in the future")
    private Instant appointmentDate;

    /**
     * Optional on create — the service defaults missing values to SCHEDULED.
     * Required on full-replacement update so the client is explicit.
     */
    private AppointmentStatus status;

    @Size(max = 1000, message = "notes must be at most 1000 characters")
    private String notes;
}
