package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDTO {

    private String lastName;
    private String address;
    private int age;
    private String email;
    private Set<String> medications;
    private Set<String> allergies;

}
