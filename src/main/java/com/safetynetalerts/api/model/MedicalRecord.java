package com.safetynetalerts.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {

    private Person person;
    private Set<String> medications;
    private Set<String> allergies;

}
