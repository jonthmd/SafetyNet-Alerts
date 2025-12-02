package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.mapper.MedicalRecordMapper;
import com.safetynetalerts.api.mapper.PersonChildMapper;
import com.safetynetalerts.api.mapper.PersonMapper;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private PersonChildMapper personChildMapper;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private PersonServiceImpl classUnderTest;


    @Test
    void getPersonsTest(){
        //GIVEN
        Person person = new Person();
        List<Person> personList = List.of(person);
        PersonDTO personDTO = new PersonDTO();
        List<PersonDTO> personDTOList = List.of(personDTO);

        when(dataRepository.getPersons()).thenReturn(personList);
        when(personMapper.personToPersonDto(person)).thenReturn(personDTO);

        //WHEN
        List<PersonDTO> result = classUnderTest.getAll();

        //THEN
        verify(personMapper).personToPersonDto(person);
        assertThat(result).isEqualTo(personDTOList);
    }

    @Test
    void getAPersonTest(){
        //GIVEN
        Person person = new Person("Jon", "TH", "", "", "", "", "");
        PersonDTO personDTO = new PersonDTO("Jon", "TH", "", "", "", "", "");
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);
        when(personMapper.personToPersonDto(person)).thenReturn(personDTO);

        //WHEN
        PersonDTO result = classUnderTest.getByFirstNameAndLastName("Jon", "TH");

        //THEN
        verify(personMapper).personToPersonDto(person);
        assertThat(result).isEqualTo(personDTO);
    }

    @Test
    void getChildrenNoAdultTest(){
        //GIVEN
        Person person = new Person("Jon", "TH", "1509 Culver St", "", "", "", "");
        PersonChildDTO personChildDTO = new PersonChildDTO("Jon", "TH", 2);
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);
        when(personChildMapper.personChildToPersonChildDTO(person)).thenReturn(personChildDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2022");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        PersonChildAlertDTO result = classUnderTest.getChildren("1509 Culver St");

        //THEN
        verify(dataRepository).getPersons();
        verify(personChildMapper).personChildToPersonChildDTO(person);
        verify(dataRepository).getMedicalRecords();
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getChildDTOList()).hasSize(1);
        assertThat(result.getFamilyDTOList()).hasSize(0);
    }

    @Test
    void getChildrenNoChildTest(){
        //GIVEN
        Person person = new Person("Jon", "TH", "1509 Culver St", "", "", "", "");
        PersonChildDTO personChildDTO = new PersonChildDTO("Jon", "TH", 22);
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);
        when(personChildMapper.personChildToPersonChildDTO(person)).thenReturn(personChildDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2002");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        //WHEN
        PersonChildAlertDTO result = classUnderTest.getChildren("1509 Culver St");

        //THEN
        verify(dataRepository).getPersons();
        verify(personChildMapper).personChildToPersonChildDTO(person);
        verify(dataRepository).getMedicalRecords();
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getChildDTOList()).hasSize(0);
        assertThat(result.getFamilyDTOList()).hasSize(1);
    }

    @Test
    void getInfoLastNameTest(){
        //GIVEN
        Person person = new Person("Jon", "TH", "", "", "", "", "");
        PersonDTO personDTO = new PersonDTO("Jon", "TH", "", "", "", "", "");
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);
        when(personMapper.personToPersonDto(person)).thenReturn(personDTO);

        MedicalRecord medicalRecord = new MedicalRecord();
        List<MedicalRecord> medicalRecordList = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setFirstName("Jon");
        medicalRecordDTO.setLastName("TH");
        medicalRecordDTO.setBirthdate("12/28/2002");
        when(dataRepository.getMedicalRecords()).thenReturn(medicalRecordList);
        when(medicalRecordMapper.medicalRecordToMedicalRecordDto(medicalRecord)).thenReturn(medicalRecordDTO);

        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setLastName(person.getLastName());
        personInfoDTO.setAddress(person.getAddress());
        personInfoDTO.setAge(22);
        personInfoDTO.setEmail(person.getEmail());
        personInfoDTO.setMedications(medicalRecordDTO.getMedications());
        personInfoDTO.setAllergies(medicalRecordDTO.getAllergies());

        //WHEN
        PersonInfoLastNameDTO result = classUnderTest.getInfoLastName("TH");

        //THEN
        verify(dataRepository).getPersons();
        verify(personMapper).personToPersonDto(person);
        verify(dataRepository).getMedicalRecords();
        verify(medicalRecordMapper).medicalRecordToMedicalRecordDto(medicalRecord);
        assertThat(result.getPersons()).isEqualTo(List.of(personInfoDTO));
    }

    @Test
    void getEmailsTest(){
        //GIVEN
        Person person = new Person("Jon", "TH", "", "CM", "", "", "jon@th.com");
        List<Person> personList = List.of(person);
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN
        PersonEmailDTO result = classUnderTest.getEmails("CM");

        //THEN
        verify(dataRepository).getPersons();
        assertThat(result.getEmail()).isEqualTo(Set.of("jon@th.com"));
    }

    @Test
    void addPersonTest(){
        //GIVEN
        PersonDTO personDTO = new PersonDTO();
        Person person = new Person("Jon", "TH", "", "", "", "", "");
        when(personMapper.personDtoToPerson(personDTO)).thenReturn(person);
        when(personMapper.personToPersonDto(person)).thenReturn(personDTO);

        //WHEN
        PersonDTO result = classUnderTest.create(personDTO);

        //THEN
        verify(personMapper).personDtoToPerson(personDTO);
        verify(personMapper).personToPersonDto(person);
        assertThat(result).isEqualTo(personDTO);
    }

    @Test
    void updatePersonTest(){
        //GIVEN
        List<Person> persons = new ArrayList<>();
        Person current = new Person("Jon", "TH", "address", "city", "zip", "phone", "email");
        persons.add(current);
        when(dataRepository.getPersons()).thenReturn(persons);

        PersonDTO updated = new PersonDTO("Jon", "TH", "a", "c", "z", "p", "e");
        when(personMapper.personToPersonDto(current)).thenReturn(updated);

        //WHEN
        PersonDTO result = classUnderTest.update("Jon", "TH", updated);

        //THEN
        verify(personMapper).personToPersonDto(current);
        assertThat(result).isEqualTo(updated);
        assertThat(current.getAddress()).isEqualTo("a");
    }

    @Test
    void deletePersonTest(){
        //GIVEN
        List<Person> personList = new ArrayList<>();
        Person person = new Person("Jon", "TH", "address", "city", "zip", "phone", "email");
        personList.add(person);
        when(dataRepository.getPersons()).thenReturn(personList);

        //WHEN
        classUnderTest.delete("Jon", "TH");

        //THEN
        verify(dataRepository).getPersons();
        assertThat(personList).doesNotContain(person);
        assertThat(personList).hasSize(0);
    }
}
