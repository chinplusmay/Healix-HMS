package com.hms.patient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hms.patient.entity.BloodGroup;
import com.hms.patient.entity.Gender;
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
public class PatientResponse {

    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private BloodGroup bloodGroup;
    private String phone;
    private String address;

    private Instant createdAt;
    private Instant updatedAt;
}
