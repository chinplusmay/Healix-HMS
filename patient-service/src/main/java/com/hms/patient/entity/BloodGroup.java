package com.hms.patient.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Standard ABO + Rh blood-group classifications.
 *
 * The {@code label} ("A+", "O-", etc.) is what clients send and receive over JSON.
 * The enum constant name is what gets persisted (so renaming display labels never
 * corrupts the DB).
 */
public enum BloodGroup {

    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    UNKNOWN("Unknown");

    private final String label;

    BloodGroup(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    /**
     * Parse "A+", "a+", or the enum name "A_POSITIVE" — all case-insensitive.
     */
    @JsonCreator
    public static BloodGroup fromString(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return Arrays.stream(values())
                .filter(bg -> bg.label.equalsIgnoreCase(trimmed) || bg.name().equalsIgnoreCase(trimmed))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid bloodGroup '" + value + "'. Allowed: A+, A-, B+, B-, AB+, AB-, O+, O-, Unknown"));
    }
}
