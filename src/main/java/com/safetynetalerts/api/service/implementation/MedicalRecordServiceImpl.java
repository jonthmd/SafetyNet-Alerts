package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.MedicalRecordDTO;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Implementations of the fire station service interface.
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final DataRepository dataRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordServiceImpl(DataRepository dataRepository, MedicalRecordMapper medicalRecordMapper) {
        this.dataRepository = dataRepository;
        this.medicalRecordMapper = medicalRecordMapper;
    }


    /**
     * Retrieves a list of medical records with their information.
     * @return A list of MedicalRecordDTO.
     */
    @Override
    public List<MedicalRecordDTO> getAll() {
        return dataRepository.getMedicalRecords()
                .stream()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .toList();
    }

    /**
     * Searches a medical record with the specified first name and last name.
     * @param firstName The first name of the medical record's person.
     * @param lastName The last name of the medical record's person.
     * @return The matching MedicalRecordDTO.
     */
    @Override
    public MedicalRecordDTO getByFirstNameAndLastName(String firstName, String lastName) {
        return dataRepository.getMedicalRecords()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(medicalRecordMapper::medicalRecordToMedicalRecordDto)
                .orElse(null);
    }

    /**
     * Creates a new medical record.
     * @param medicalRecordDTO Mapped object containing the person details to be created.
     * @return MedicalRecordDTO, the created medical record.
     */
    @Override
    public MedicalRecordDTO create(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = medicalRecordMapper.medicalRecordDtoToMedicalRecord(medicalRecordDTO);
        dataRepository.getMedicalRecords().add(medicalRecord);
        return medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord);
    }

    /**
     * Updates an existing medical record based on first name, last name and medicalRecordDTO.
     * @param firstName The first name of the person to update.
     * @param lastName The last name of the person to update.
     * @param medicalRecordDTO Mapped object containing the medical record details to be updated.
     * @return MedicalRecordDTO, the updated medical record.
     */
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

    /**
     * Deletes an existing medical record based on the first name and last name of the person.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     */
    @Override
    public void delete(String firstName, String lastName) {
         dataRepository.getMedicalRecords()
                .removeIf(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName));
    }
}
