package com.safetynetalerts.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private Set<String> medications;
    private Set<String> allergies;

}