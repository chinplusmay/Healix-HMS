package com.hms.doctor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hms.doctor.entity.Availability;
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
public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
    private Integer experience;
    private Availability availability;
    private String email;
    private String phone;

    private Instant createdAt;
    private Instant updatedAt;
}
