package com.safetynetalerts.api.model;

import lombok.Data;

import java.util.List;

@Data
public class POJO {

    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;

}