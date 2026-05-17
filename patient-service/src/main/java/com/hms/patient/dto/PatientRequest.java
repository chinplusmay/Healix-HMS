package com.hms.patient.dto;

import com.hms.patient.entity.BloodGroup;
import com.hms.patient.entity.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @NotNull(message = "age is required")
    @Min(value = 0, message = "age must be >= 0")
    @Max(value = 150, message = "age must be <= 150")
    private Integer age;

    @NotNull(message = "gender is required (MALE, FEMALE, OTHER)")
    private Gender gender;

    @NotNull(message = "bloodGroup is required (A+, A-, B+, B-, AB+, AB-, O+, O-, Unknown)")
    private BloodGroup bloodGroup;

    /**
     * Permissive E.164-style phone pattern: optional leading '+', then 7–15 digits,
     * spaces / dashes / parentheses allowed for human readability.
     */
    @NotBlank(message = "phone is required")
    @Pattern(
            regexp = "^\\+?[0-9 ()\\-]{7,20}$",
            message = "phone must be a valid phone number (digits, optional leading '+', 7-20 chars)"
    )
    private String phone;

    @NotBlank(message = "address is required")
    @Size(max = 500, message = "address must be at most 500 characters")
    private String address;
}
