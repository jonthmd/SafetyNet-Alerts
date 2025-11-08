package com.safetynetalerts.api.model;

import lombok.Data;

import java.util.Set;

@Data
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private Set<String> medications;
    private Set<String> allergies;

}