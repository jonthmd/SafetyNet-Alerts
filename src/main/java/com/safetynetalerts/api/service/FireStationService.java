package com.safetynetalerts.api.service;

import com.safetynetalerts.api.dto.FireStationDTO;
import com.safetynetalerts.api.dto.PersonDTO;

import java.util.List;

public interface FireStationService {

    List<FireStationDTO> getAll();
    FireStationDTO getByAddress(String address);
    FireStationDTO create(FireStationDTO fireStationDTO);
    FireStationDTO update(String address, FireStationDTO fireStationDTO);
    void delete(String address);

}
