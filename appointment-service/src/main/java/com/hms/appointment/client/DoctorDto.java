package com.hms.appointment.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Minimal mirror of doctor-service's DoctorResponse — same shape-tolerance
 * pattern as PatientDto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDto {
    private Long id;
    private String name;
    private String specialization;
    private String availability;
}
