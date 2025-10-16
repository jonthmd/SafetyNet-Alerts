package com.safetynetalerts.api.model;

import lombok.Data;

import java.util.Set;

@Data
public class MedicalRecord {

    private Person person;
    private Set<String> medications;
    private Set<String> allergies;

}
