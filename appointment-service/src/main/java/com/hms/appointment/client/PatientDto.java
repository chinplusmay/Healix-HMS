package com.hms.appointment.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Minimal mirror of patient-service's PatientResponse — we only deserialize the
 * fields we actually care about. {@code @JsonIgnoreProperties(ignoreUnknown = true)}
 * is the lifeline that lets patient-service add new fields without breaking us.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDto {
    private Long id;
    private String name;
    private String phone;
}
