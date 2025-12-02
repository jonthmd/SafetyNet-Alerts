package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO used to represent a person with phone number,
 * age, and medical record.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirePersonDTO {

    private String lastName;
    private String phone;
    private int age;
    private Set<String> medications;
    private Set<String> allergies;

}

