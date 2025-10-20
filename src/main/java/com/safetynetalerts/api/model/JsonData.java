package com.safetynetalerts.api.model;

import lombok.Data;

import java.util.List;

@Data
public class JsonData {

    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;

}