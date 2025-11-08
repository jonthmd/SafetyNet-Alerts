package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationStatsDTO {

    private List<FireStationPersonDTO> listPersons; // prénom, nom, adresse, numéro de téléphone

    private int adults;
    private int children;

}
