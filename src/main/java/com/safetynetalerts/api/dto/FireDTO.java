package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO used to represent a list of fire stations and a list of persons with phone number,
 * age, and medical record.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireDTO {

    private List<String> station;
    private List<FirePersonDTO> persons;

}
