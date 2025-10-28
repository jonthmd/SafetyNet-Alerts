package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
