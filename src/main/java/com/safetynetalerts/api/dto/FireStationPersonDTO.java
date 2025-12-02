package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent a person with first name, last name,
 * address and phone number.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationPersonDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

}
