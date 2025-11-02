package com.safetynetalerts.api.service;

import com.safetynetalerts.api.dto.MedicalRecordDTO;

import java.util.List;

public interface MedicalRecordService {

    List<MedicalRecordDTO> getAll();
    MedicalRecordDTO getByFirstNameAndLastName(String firstName, String lastName);
    MedicalRecordDTO create(MedicalRecordDTO medicalRecordDTO);
    MedicalRecordDTO update(String firstName, String lastName, MedicalRecordDTO medicalRecordDTO);
    void delete(String firstName, String lastName);

}
