package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.MedicalRecordDTO;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final DataRepository dataRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordServiceImpl(DataRepository dataRepository, MedicalRecordMapper medicalRecordMapper) {
        this.dataRepository = dataRepository;
        this.medicalRecordMapper = medicalRecordMapper;
    }


    @Override
    public List<MedicalRecordDTO> getAll() {
        return dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .toList();
    }

    @Override
    public MedicalRecordDTO getByFirstNameAndLastName(String firstName, String lastName) {
        return dataRepository.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .orElse(null);
    }

    @Override
    public MedicalRecordDTO create(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = medicalRecordMapper.medicalRecordDtoToMedicalRecord(medicalRecordDTO);
        dataRepository.getMedicalRecords().add(medicalRecord);
        return medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord);
    }

    @Override
    public MedicalRecordDTO update(String firstName, String lastName, MedicalRecordDTO medicalRecordDTO) {
        return dataRepository.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(current -> {
                    current.setBirthdate(medicalRecordDTO.getBirthdate());
                    current.setMedications(medicalRecordDTO.getMedications());
                    current.setAllergies(medicalRecordDTO.getAllergies());
                    return medicalRecordMapper.medicalRecordToMedicalRecordDto(current);
                })
                .orElse(null);
    }

    @Override
    public void delete(String firstName, String lastName) {
         dataRepository.getMedicalRecords()
                .removeIf(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName));
    }
}
