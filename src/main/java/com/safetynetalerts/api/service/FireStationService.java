package com.safetynetalerts.api.service;

import com.safetynetalerts.api.dto.*;

import java.util.List;

public interface FireStationService {

    List<FireStationDTO> getAll();
    FireStationDTO getByAddress(String address);
    FireStationStatsDTO getByStationNumber(String stationNumber);
    FireDTO getRecordsPersonByAddress(String address);
    FireStationFloodDTO getHomes(List<String> stationNumber);
    FireStationDTO create(FireStationDTO fireStationDTO);
    FireStationDTO update(String address, FireStationDTO fireStationDTO);
    void delete(String address);

}
