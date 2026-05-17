package com.hms.appointment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hms.appointment.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponse {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Instant appointmentDate;
    private AppointmentStatus status;
    private String notes;

    private Instant createdAt;
    private Instant updatedAt;
}
