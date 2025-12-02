package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO used to represent a list of children and a list of adults.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationStatsDTO {

    private List<FireStationPersonDTO> listPersons;

    private int adults;
    private int children;

}
