package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO used to represent a list of addresses and a list of persons with phone number,
 * age, and medical record.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationFloodDTO {

    private List<String> addresses;
    private List<FirePersonDTO> persons;

}
