package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent first name and last name of family members.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonFamilyDTO {

    private String firstName;
    private String lastName;

}
