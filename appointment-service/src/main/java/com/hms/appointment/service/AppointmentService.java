package com.hms.appointment.service;

import com.hms.appointment.client.DoctorClient;
import com.hms.appointment.client.PatientClient;
import com.hms.appointment.dto.AppointmentRequest;
import com.hms.appointment.dto.AppointmentResponse;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.entity.AppointmentStatus;
import com.hms.appointment.exception.ExternalServiceException;
import com.hms.appointment.exception.IllegalStatusTransitionException;
import com.hms.appointment.exception.InvalidReferenceException;
import com.hms.appointment.exception.ResourceNotFoundException;
import com.hms.appointment.repository.AppointmentRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentResponse create(AppointmentRequest request) {
        verifyPatientExists(request.getPatientId());
        verifyDoctorExists(request.getDoctorId());

        Appointment appointment = Appointment.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .status(request.getStatus() != null ? request.getStatus() : AppointmentStatus.SCHEDULED)
                .notes(request.getNotes())
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        log.info("Created appointment id={} patientId={} doctorId={} on={}",
                saved.getId(), saved.getPatientId(), saved.getDoctorId(), saved.getAppointmentDate());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id) {
        return toResponse(loadOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<AppointmentResponse> findAll(Long patientId, Long doctorId, AppointmentStatus status, Pageable pageable) {
        Page<Appointment> page;
        if (patientId != null && status != null) {
            page = appointmentRepository.findByPatientIdAndStatus(patientId, status, pageable);
        } else if (doctorId != null && status != null) {
            page = appointmentRepository.findByDoctorIdAndStatus(doctorId, status, pageable);
        } else if (patientId != null) {
            page = appointmentRepository.findByPatientId(patientId, pageable);
        } else if (doctorId != null) {
            page = appointmentRepository.findByDoctorId(doctorId, pageable);
        } else if (status != null) {
            page = appointmentRepository.findByStatus(status, pageable);
        } else {
            page = appointmentRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment existing = loadOrThrow(id);

        // Re-verify references if the client is changing them.
        if (!existing.getPatientId().equals(request.getPatientId())) {
            verifyPatientExists(request.getPatientId());
        }
        if (!existing.getDoctorId().equals(request.getDoctorId())) {
            verifyDoctorExists(request.getDoctorId());
        }

        existing.setPatientId(request.getPatientId());
        existing.setDoctorId(request.getDoctorId());
        existing.setAppointmentDate(request.getAppointmentDate());
        existing.setNotes(request.getNotes());
        if (request.getStatus() != null) {
            assertLegalTransition(existing.getStatus(), request.getStatus());
            existing.setStatus(request.getStatus());
        }

        Appointment saved = appointmentRepository.save(existing);
        log.info("Updated appointment id={}", saved.getId());
        return toResponse(saved);
    }

    public AppointmentResponse updateStatus(Long id, AppointmentStatus newStatus) {
        Appointment existing = loadOrThrow(id);
        assertLegalTransition(existing.getStatus(), newStatus);
        existing.setStatus(newStatus);
        Appointment saved = appointmentRepository.save(existing);
        log.info("Appointment id={} status {} -> {}", saved.getId(), existing.getStatus(), newStatus);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id=" + id);
        }
        appointmentRepository.deleteById(id);
        log.info("Deleted appointment id={}", id);
    }

    // ---------- helpers ----------

    private Appointment loadOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id=" + id));
    }

    /**
     * Lifecycle rules:
     *   SCHEDULED  -> COMPLETED  (allowed)
     *   SCHEDULED  -> CANCELLED  (allowed)
     *   COMPLETED  -> *          (denied — terminal)
     *   CANCELLED  -> *          (denied — terminal)
     *   X -> X (no-op)           (allowed)
     */
    private void assertLegalTransition(AppointmentStatus current, AppointmentStatus target) {
        if (current == target) {
            return;
        }
        if (current != AppointmentStatus.SCHEDULED) {
            throw new IllegalStatusTransitionException(
                    "Cannot move appointment from " + current + " to " + target
                            + " — '" + current + "' is a terminal state.");
        }
    }

    private void verifyPatientExists(Long patientId) {
        try {
            patientClient.getPatientById(patientId);
        } catch (FeignException.NotFound e) {
            throw new InvalidReferenceException("No patient exists with id=" + patientId);
        } catch (FeignException e) {
            throw new ExternalServiceException(
                    "patient-service is unavailable (status " + e.status() + ")", e);
        }
    }

    private void verifyDoctorExists(Long doctorId) {
        try {
            doctorClient.getDoctorById(doctorId);
        } catch (FeignException.NotFound e) {
            throw new InvalidReferenceException("No doctor exists with id=" + doctorId);
        } catch (FeignException e) {
            throw new ExternalServiceException(
                    "doctor-service is unavailable (status " + e.status() + ")", e);
        }
    }

    private AppointmentResponse toResponse(Appointment a) {
        return AppointmentResponse.builder()
                .id(a.getId())
                .patientId(a.getPatientId())
                .doctorId(a.getDoctorId())
                .appointmentDate(a.getAppointmentDate())
                .status(a.getStatus())
                .notes(a.getNotes())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }
}
