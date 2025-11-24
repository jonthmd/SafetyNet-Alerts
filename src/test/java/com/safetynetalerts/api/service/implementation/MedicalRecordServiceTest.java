package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.MedicalRecordDTO;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private MedicalRecordServiceImpl classUnderTest;

    @Test
    void getAllMedicalRecordsTest(){
        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        List<MedicalRecordDTO> medicalRecordDTOList = List.of(medicalRecordDTO);
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        List<MedicalRecordDTO> result = classUnderTest.getAll();

        //THEN
        verify(dataRepository).getMedicalRecords();
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result).isEqualTo(medicalRecordDTOList);
    }

    @Test
    void getMedicalRecordByFirstNameAndLastName(){
        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Jon");
        medicalRecord.setLastName("TH");
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        MedicalRecordDTO result = classUnderTest.getByFirstNameAndLastName("Jon", "TH");

        //THEN
        verify(dataRepository).getMedicalRecords();
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result).isEqualTo(medicalRecordDTO);
    }

    @Test
    void addMedicalRecordTest(){
        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord();
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        when(medicalRecordMapper.medicalRecordDtoToMedicalRecord(medicalRecordDTO)).thenReturn(medicalRecord);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        MedicalRecordDTO result = classUnderTest.create(medicalRecordDTO);

        //THEN
        verify(medicalRecordMapper).medicalRecordDtoToMedicalRecord(medicalRecordDTO);
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result).isEqualTo(medicalRecordDTO);
    }

    @Test
    void updateMedicalRecordTest(){
        //GIVEN
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Jon");
        medicalRecord.setLastName("TH");
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/12/1992");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        MedicalRecordDTO result = classUnderTest.update("Jon", "TH", medicalRecordDTO);

        //THEN
        verify(dataRepository).getMedicalRecords();
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result).isEqualTo(medicalRecordDTO);
        assertThat(result.getBirthdate()).isEqualTo("12/12/1992");
    }

    @Test
    void deleteMedicalRecordTest(){
        //GIVEN
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Jon");
        medicalRecord.setLastName("TH");
        medicalRecordList.add(medicalRecord);
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);

        //WHEN
        classUnderTest.delete("Jon", "TH");

        //THEN
        verify(dataRepository).getMedicalRecords();
        assertThat(medicalRecordList).doesNotContain(medicalRecord);
        assertThat(medicalRecordList).hasSize(0);
    }
}