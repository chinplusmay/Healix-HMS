package com.hms.appointment.dto;

import com.hms.appointment.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Body for PATCH /appointments/{id}/status — the most common write operation
 * after creation. Doing it via PATCH keeps full updates and status changes distinct
 * in the audit log.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentStatusUpdateRequest {

    @NotNull(message = "status is required (SCHEDULED, COMPLETED, CANCELLED)")
    private AppointmentStatus status;
}
