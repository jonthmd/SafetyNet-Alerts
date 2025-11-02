package com.safetynetalerts.api.mapper;

import com.safetynetalerts.api.dto.MedicalRecordDTO;
import com.safetynetalerts.api.model.MedicalRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    MedicalRecordDTO medicalRecordToMedicalRecordDto(MedicalRecord medicalRecord);
    MedicalRecord medicalRecordDtoToMedicalRecord(MedicalRecordDTO medicalRecordDTO);

}
