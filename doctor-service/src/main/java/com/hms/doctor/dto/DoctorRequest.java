package com.hms.doctor.dto;

import com.hms.doctor.entity.Availability;
import jakarta.validation.constraints.Email;
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
public class DoctorRequest {

    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @NotBlank(message = "specialization is required")
    @Size(max = 80, message = "specialization must be at most 80 characters")
    private String specialization;

    @NotNull(message = "experience is required")
    @Min(value = 0, message = "experience must be >= 0")
    @Max(value = 70, message = "experience must be <= 70 years")
    private Integer experience;

    @NotNull(message = "availability is required (AVAILABLE, UNAVAILABLE, ON_LEAVE)")
    private Availability availability;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    @Size(max = 160, message = "email must be at most 160 characters")
    private String email;

    /**
     * Permissive E.164-style phone pattern: optional leading '+', then 7-20 chars
     * of digits / spaces / dashes / parentheses.
     */
    @NotBlank(message = "phone is required")
    @Pattern(
            regexp = "^\\+?[0-9 ()\\-]{7,20}$",
            message = "phone must be a valid phone number (digits, optional leading '+', 7-20 chars)"
    )
    private String phone;
}
