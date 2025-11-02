package com.safetynetalerts.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.safetynetalerts.api.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDTO {

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Set<String> medications;
    private Set<String> allergies;

}
