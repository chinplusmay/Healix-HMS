package com.hms.patient.service;

import com.hms.patient.dto.PatientRequest;
import com.hms.patient.dto.PatientResponse;
import com.hms.patient.entity.Patient;
import com.hms.patient.exception.ResourceNotFoundException;
import com.hms.patient.repository.PatientRepository;
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
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponse create(PatientRequest request) {
        Patient saved = patientRepository.save(toEntity(request));
        log.info("Created patient id={} name='{}'", saved.getId(), saved.getName());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PatientResponse findById(Long id) {
        return toResponse(loadOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<PatientResponse> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable).map(this::toResponse);
    }

    public PatientResponse update(Long id, PatientRequest request) {
        Patient existing = loadOrThrow(id);
        applyChanges(existing, request);
        Patient saved = patientRepository.save(existing);
        log.info("Updated patient id={}", saved.getId());
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id=" + id);
        }
        patientRepository.deleteById(id);
        log.info("Deleted patient id={}", id);
    }

    // ---------- helpers ----------

    private Patient loadOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id=" + id));
    }

    private Patient toEntity(PatientRequest req) {
        return Patient.builder()
                .name(req.getName().trim())
                .age(req.getAge())
                .gender(req.getGender())
                .bloodGroup(req.getBloodGroup())
                .phone(req.getPhone().trim())
                .address(req.getAddress().trim())
                .build();
    }

    private void applyChanges(Patient existing, PatientRequest req) {
        existing.setName(req.getName().trim());
        existing.setAge(req.getAge());
        existing.setGender(req.getGender());
        existing.setBloodGroup(req.getBloodGroup());
        existing.setPhone(req.getPhone().trim());
        existing.setAddress(req.getAddress().trim());
    }

    private PatientResponse toResponse(Patient p) {
        return PatientResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .age(p.getAge())
                .gender(p.getGender())
                .bloodGroup(p.getBloodGroup())
                .phone(p.getPhone())
                .address(p.getAddress())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
