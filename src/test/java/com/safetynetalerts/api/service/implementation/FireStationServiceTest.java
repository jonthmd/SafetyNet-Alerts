package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.mapper.FireStationMapper;
import com.safetynetalerts.api.mapper.FireStationPersonMapper;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.mapper.PersonMapper;
import com.safetynetalerts.api.model.FireStation;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.model.Person;
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
class FireStationServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private FireStationMapper fireStationMapper;

    @Mock
    private FireStationPersonMapper fireStationPersonMapper;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private FireStationServiceImpl classUnderTest;

    @Test
    void getFireStationsTest(){
        //GIVEN
        FireStation fireStation = new FireStation();
        List<FireStation> fireStationList = List.of(fireStation);

        FireStationDTO fireStationDTO = new FireStationDTO();
        List<FireStationDTO> fireStationDTOList = List.of(fireStationDTO);

        when(dataRepository.getFireStations()).thenReturn(fireStationList);
        when(fireStationMapper.fireStationToFireStationDto(fireStation)).thenReturn(fireStationDTO);

        //WHEN
        List<FireStationDTO> result = classUnderTest.getAll();

        //THEN
        verify(dataRepository).getFireStations();
        verify(fireStationMapper).fireStationToFireStationDto(fireStation);
        assertThat(result).isEqualTo(fireStationDTOList);
    }

    @Test
    void getFireStationByAddressTest(){
        //GIVEN
        FireStation fireStation = new FireStation("1509 Culver St","");
        List<FireStation> fireStationList = List.of(fireStation);

        FireStationDTO fireStationDTO = new FireStationDTO();

        when(dataRepository.getFireStations()).thenReturn(fireStationList);
        when(fireStationMapper.fireStationToFireStationDto(fireStation)).thenReturn(fireStationDTO);

        //WHEN
        FireStationDTO result = classUnderTest.getByAddress("1509 Culver St");

        //THEN
        verify(dataRepository).getFireStations();
        verify(fireStationMapper).fireStationToFireStationDto(fireStation);
        assertThat(result).isEqualTo(fireStationDTO);
    }

    @Test
    void getFireStationByStationNumberChildTest(){
        //GIVEN
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        List<FireStation> fireStationList = List.of(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        Person person = new Person("Jon", "TH", "1509 Culver St", "", "","", "");
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);

        FireStationPersonDTO fireStationPersonDTO = new FireStationPersonDTO("Jon","TH","1509 Culver St","");
        when(fireStationPersonMapper.fireStationPersonToFireStationPersonDto(person)).thenReturn(fireStationPersonDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2022");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        FireStationStatsDTO result = classUnderTest.getByStationNumber("3");

        //THEN
        verify(dataRepository).getFireStations();
        verify(dataRepository).getPersons();
        verify(dataRepository).getMedicalRecords();
        verify(fireStationPersonMapper).fireStationPersonToFireStationPersonDto(person);
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getListPersons()).hasSize(1);
        assertThat(result.getChildren()).isEqualTo(1);
        assertThat(result.getAdults()).isEqualTo(0);
    }

    @Test
    void getFireStationByStationNumberAdultTest(){
        //GIVEN
        FireStation fireStation = new FireStation("1509 Culver St", "3");
        List<FireStation> fireStationList = List.of(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        Person person = new Person("Jon", "TH", "1509 Culver St", "", "","", "");
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);

        FireStationPersonDTO fireStationPersonDTO = new FireStationPersonDTO("Jon","TH","1509 Culver St","");
        when(fireStationPersonMapper.fireStationPersonToFireStationPersonDto(person)).thenReturn(fireStationPersonDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2000");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        FireStationStatsDTO result = classUnderTest.getByStationNumber("3");

        //THEN
        verify(dataRepository).getFireStations();
        verify(dataRepository).getPersons();
        verify(dataRepository).getMedicalRecords();
        verify(fireStationPersonMapper).fireStationPersonToFireStationPersonDto(person);
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getListPersons()).hasSize(1);
        assertThat(result.getChildren()).isEqualTo(0);
        assertThat(result.getAdults()).isEqualTo(1);
    }

    @Test
    void getRecordsPersonByAddressTest(){
        //GIVEN
        FireStation fireStation = new FireStation("1509 Culver St","3");
        List<FireStation> fireStationList = List.of(fireStation);
        List<String> stationList = List.of("3");
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        Person person = new Person("Jon","TH","1509 Culver St","","","","");
        List<Person> personList = List.of(person);
        PersonDTO personDTO = new PersonDTO("Jon","TH","1509 Culver St","","","","");
        when(dataRepository.getPersons()).thenReturn(personList);
        when(personMapper.personToPersonDto(person)).thenReturn(personDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2022");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        FirePersonDTO firePersonDTO = new FirePersonDTO();
        firePersonDTO.setLastName("TH");
        firePersonDTO.setPhone("");
        firePersonDTO.setAge(2);
        firePersonDTO.setMedications(medicalRecordDTO.getMedications());
        firePersonDTO.setAllergies(medicalRecordDTO.getAllergies());

        List<FirePersonDTO> firePersonDTOList = List.of(firePersonDTO);

        FireDTO fireDTO = new FireDTO();
        fireDTO.setStation(stationList);
        fireDTO.setPersons(firePersonDTOList);

        //WHEN
        FireDTO result = classUnderTest.getRecordsPersonByAddress("1509 Culver St");

        //THEN
        verify(dataRepository).getFireStations();
        verify(dataRepository).getPersons();
        verify(dataRepository).getMedicalRecords();
        verify(personMapper).personToPersonDto(person);
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getStation()).isEqualTo(List.of("3"));
        assertThat(result.getPersons()).hasSize(1);
    }

    @Test
    void getFireStationFloodHomes(){
        //GIVEN
        FireStation fireStation = new FireStation("1509 Culver St","3");
        List<FireStation> fireStationList = List.of(fireStation);
        List<String> addressesList = List.of("1509 Culver St");
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        Person person = new Person("Jon","TH","1509 Culver St","","","","");
        List<Person> personList = List.of(person);
        PersonDTO personDTO = new PersonDTO("Jon","TH","1509 Culver St","","","","");
        when(dataRepository.getPersons()).thenReturn(personList);
        when(personMapper.personToPersonDto(person)).thenReturn(personDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2022");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        FirePersonDTO firePersonDTO = new FirePersonDTO();
        firePersonDTO.setLastName("TH");
        firePersonDTO.setPhone("");
        firePersonDTO.setAge(2);
        firePersonDTO.setMedications(medicalRecordDTO.getMedications());
        firePersonDTO.setAllergies(medicalRecordDTO.getAllergies());

        List<FirePersonDTO> firePersonDTOList = List.of(firePersonDTO);

        FireStationFloodDTO fireStationFloodDTO = new FireStationFloodDTO();
        fireStationFloodDTO.setAddresses(addressesList);
        fireStationFloodDTO.setPersons(firePersonDTOList);

        //WHEN
        List<String> stationNumbers = List.of("3");
        FireStationFloodDTO result = classUnderTest.getHomes(stationNumbers);

        //THEN
        verify(dataRepository).getFireStations();
        verify(dataRepository).getPersons();
        verify(dataRepository).getMedicalRecords();
        verify(personMapper).personToPersonDto(person);
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getAddresses()).isEqualTo(List.of("1509 Culver St"));
        assertThat(result.getPersons()).hasSize(1);
    }

    @Test
    void addFireStationTest(){
        //GIVEN
        FireStation fireStation = new FireStation();
        FireStationDTO fireStationDTO = new FireStationDTO();
        when(fireStationMapper.fireStationDtoToFireStation(fireStationDTO)).thenReturn(fireStation);
        when(fireStationMapper.fireStationToFireStationDto(fireStation)).thenReturn(fireStationDTO);

        //WHEN
        FireStationDTO result = classUnderTest.create(fireStationDTO);

        //THEN
        verify(fireStationMapper).fireStationDtoToFireStation(fireStationDTO);
        verify(fireStationMapper).fireStationToFireStationDto(fireStation);
        assertThat(result).isEqualTo(fireStationDTO);
    }

    @Test
    void updateFireStationTest(){
        //GIVEN
        List<FireStation> fireStationList = new ArrayList<>();
        FireStation fireStation = new FireStation("1509 Culver St","3");
        fireStationList.add(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        FireStationDTO fireStationDTO = new FireStationDTO("1509 Culver St","1");
        when(fireStationMapper.fireStationToFireStationDto(fireStation)).thenReturn(fireStationDTO);

        //WHEN
        FireStationDTO result = classUnderTest.update("1509 Culver St", fireStationDTO);

        //THEN
        verify(dataRepository).getFireStations();
        verify(fireStationMapper).fireStationToFireStationDto(fireStation);
        assertThat(result).isEqualTo(fireStationDTO);
        assertThat(result.getStation()).isEqualTo("1");
    }

    @Test
    void deleteFireStationTest(){
        //GIVEN
        List<FireStation> fireStationList = new ArrayList<>();
        FireStation fireStation = new FireStation("1509 Culver St","3");
        fireStationList.add(fireStation);
        when(dataRepository.getFireStations()).thenReturn(fireStationList);

        //WHEN
        classUnderTest.delete("1509 Culver St");

        //THEN
        verify(dataRepository).getFireStations();
        assertThat(fireStationList).doesNotContain(fireStation);
        assertThat(fireStationList).hasSize(0);
    }
}