package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent a child with first name, last name and age.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonChildDTO {

    private String firstName;
    private String lastName;
    private int age;

}
