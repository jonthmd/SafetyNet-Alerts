package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.FireStationDTO;
import com.safetynetalerts.api.mapper.FireStationMapper;
import com.safetynetalerts.api.model.FireStation;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.FireStationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {

    private final DataRepository dataRepository;
    private final FireStationMapper fireStationMapper;

    public FireStationServiceImpl(DataRepository dataRepository, FireStationMapper fireStationMapper) {
        this.dataRepository = dataRepository;
        this.fireStationMapper = fireStationMapper;
    }


    @Override
    public List<FireStationDTO> getAll() {
        return dataRepository.getFireStations()
                .stream()
                .map(fireStationMapper::fireStationToFireStationDto)
                .toList();
    }

    @Override
    public FireStationDTO getByAddress(String address) {
        return dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .map(fireStationMapper::fireStationToFireStationDto)
                .orElse(null);
    }

    @Override
    public FireStationDTO create(FireStationDTO fireStationDTO) {
        FireStation fireStation = fireStationMapper.fireStationDtoToFireStation(fireStationDTO);
        dataRepository.getFireStations().add(fireStation);
        return fireStationMapper.fireStationToFireStationDto(fireStation);
    }

    @Override
    public FireStationDTO update(String address, FireStationDTO fireStationDTO) {
        return dataRepository.getFireStations()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .map(current -> {
                    current.setStation(fireStationDTO.getStation());
                    return fireStationMapper.fireStationToFireStationDto(current);
                })
                .orElse(null);
    }

    @Override
    public void delete(String address) {
        dataRepository.getFireStations()
                .removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
    }

}
