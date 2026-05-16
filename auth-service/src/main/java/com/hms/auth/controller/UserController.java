package com.hms.auth.controller;

import com.hms.auth.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Demonstrates role-based authorization. These endpoints exist mostly as a
 * functional smoke test — in a real HMS the doctor/patient logic lives in
 * the respective domain microservices, called via the gateway.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "role", user.getRole()
        ));
    }

    @GetMapping("/admin/ping")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> adminPing() {
        return ResponseEntity.ok(Map.of("scope", "admin-only", "status", "ok"));
    }

    @GetMapping("/doctor/ping")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Map<String, String>> doctorPing() {
        return ResponseEntity.ok(Map.of("scope", "doctor-only", "status", "ok"));
    }

    @GetMapping("/patient/ping")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Map<String, String>> patientPing() {
        return ResponseEntity.ok(Map.of("scope", "patient-only", "status", "ok"));
    }
}
