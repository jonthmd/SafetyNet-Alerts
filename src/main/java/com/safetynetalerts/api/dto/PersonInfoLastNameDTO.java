package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO used to represent a list of person with their information including address, age,
 * email and medical record.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoLastNameDTO {

    private List<PersonInfoDTO> persons;

}
