package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationDTO {

    private String address;
    private int station;

}
