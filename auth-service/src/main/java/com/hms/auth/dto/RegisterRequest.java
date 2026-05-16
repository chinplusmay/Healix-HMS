package com.hms.auth.dto;

import com.hms.auth.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "firstName is required")
    @Size(max = 80)
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Size(max = 80)
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    @Size(max = 160)
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 100, message = "password must be between 8 and 100 characters")
    private String password;

    /**
     * Optional. Defaults to PATIENT on the server side if omitted.
     * In production you'd typically restrict who can request ADMIN/DOCTOR roles.
     */
    private Role role;
}
