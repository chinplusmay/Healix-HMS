package com.hms.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Declarative HTTP client for patient-service.
 *
 * The {@code name} matches the Eureka-registered application name
 * (spring.application.name on patient-service). Spring Cloud LoadBalancer
 * resolves it to a live instance — no hardcoded host or port.
 */
@FeignClient(name = "patient-service", contextId = "patientClient")
public interface PatientClient {

    @GetMapping("/api/v1/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") Long id);
}
