package com.safetynetalerts.api.mapper;

import com.safetynetalerts.api.dto.FireStationDTO;
import com.safetynetalerts.api.model.FireStation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FireStationMapper {

    FireStationDTO fireStationToFireStationDto(FireStation fireStation);
    FireStation fireStationDtoToFireStation(FireStationDTO fireStationDTO);

}
