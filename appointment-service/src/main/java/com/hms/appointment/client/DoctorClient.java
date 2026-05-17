package com.hms.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Declarative HTTP client for doctor-service. Service-discovery routed via Eureka.
 */
@FeignClient(name = "doctor-service", contextId = "doctorClient")
public interface DoctorClient {

    @GetMapping("/api/v1/doctors/{id}")
    DoctorDto getDoctorById(@PathVariable("id") Long id);
}
