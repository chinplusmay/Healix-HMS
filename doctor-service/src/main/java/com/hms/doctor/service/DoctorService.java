package com.hms.doctor.service;

import com.hms.doctor.dto.DoctorRequest;
import com.hms.doctor.dto.DoctorResponse;
import com.hms.doctor.entity.Availability;
import com.hms.doctor.entity.Doctor;
import com.hms.doctor.exception.EmailAlreadyExistsException;
import com.hms.doctor.exception.ResourceNotFoundException;
import com.hms.doctor.repository.DoctorRepository;
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
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorResponse create(DoctorRequest request) {
        String normalizedEmail = request.getEmail().toLowerCase().trim();
        if (doctorRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException("Doctor already registered with email: " + normalizedEmail);
        }

        Doctor saved = doctorRepository.save(toEntity(request, normalizedEmail));
        log.info("Created doctor id={} name='{}' specialization='{}'",
                saved.getId(), saved.getName(), saved.getSpecialization());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public DoctorResponse findById(Long id) {
        return toResponse(loadOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponse> findAll(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponse> findBySpecializationAndAvailability(String specialization,
                                                                    Availability availability,
                                                                    Pageable pageable) {
        return doctorRepository
                .findBySpecializationIgnoreCaseAndAvailability(specialization, availability, pageable)
                .map(this::toResponse);
    }

    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor existing = loadOrThrow(id);
        String normalizedEmail = request.getEmail().toLowerCase().trim();

        // Allow keeping the same email; reject if it's now taken by a *different* doctor.
        if (!existing.getEmail().equalsIgnoreCase(normalizedEmail)
                && doctorRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException("Email already in use: " + normalizedEmail);
        }

        applyChanges(existing, request, normalizedEmail);
        Doctor saved = doctorRepository.save(existing);
        log.info("Updated doctor id={}", saved.getId());
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id=" + id);
        }
        doctorRepository.deleteById(id);
        log.info("Deleted doctor id={}", id);
    }

    // ---------- helpers ----------

    private Doctor loadOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id=" + id));
    }

    private Doctor toEntity(DoctorRequest req, String normalizedEmail) {
        return Doctor.builder()
                .name(req.getName().trim())
                .specialization(req.getSpecialization().trim())
                .experience(req.getExperience())
                .availability(req.getAvailability())
                .email(normalizedEmail)
                .phone(req.getPhone().trim())
                .build();
    }

    private void applyChanges(Doctor existing, DoctorRequest req, String normalizedEmail) {
        existing.setName(req.getName().trim());
        existing.setSpecialization(req.getSpecialization().trim());
        existing.setExperience(req.getExperience());
        existing.setAvailability(req.getAvailability());
        existing.setEmail(normalizedEmail);
        existing.setPhone(req.getPhone().trim());
    }

    private DoctorResponse toResponse(Doctor d) {
        return DoctorResponse.builder()
                .id(d.getId())
                .name(d.getName())
                .specialization(d.getSpecialization())
                .experience(d.getExperience())
                .availability(d.getAvailability())
                .email(d.getEmail())
                .phone(d.getPhone())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}
