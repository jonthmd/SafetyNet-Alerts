package com.safetynetalerts.api;

import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.mapper.PersonMapper;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.implementation.PersonServiceImpl;
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
public class PersonServiceTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl classUnderTest;


    @Test
    public void getPersonsTest(){

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
    public void getAPersonTest(){

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
    public void addPersonTest(){

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
    public void updatePersonTest(){

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
    public void deletePersonTest(){

        //GIVEN
        List<Person> persons = new ArrayList<>();
        Person person = new Person("Jon", "TH", "address", "city", "zip", "phone", "email");
        persons.add(person);
        when(dataRepository.getPersons()).thenReturn(persons);

        //WHEN
        classUnderTest.delete("Jon", "TH");

        //THEN
        assertThat(persons).doesNotContain(person);

    }
}
