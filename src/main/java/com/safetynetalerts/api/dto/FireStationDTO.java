package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to represent the fire station model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationDTO {

    private String address;
    private String station;

}
