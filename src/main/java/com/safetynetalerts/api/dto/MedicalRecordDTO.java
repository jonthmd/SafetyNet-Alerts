package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO used to represent the medical record model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDTO {

    private String firstName;
    private String lastName;
    private String birthdate;
    private Set<String> medications;
    private Set<String> allergies;

}
