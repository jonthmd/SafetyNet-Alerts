package com.safetynetalerts.api.mapper;


import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.model.Person;

import java.util.List;

public class PersonNameMapper {

    //tester mapstruct

    public static PersonDTO toNameDTO(Person person) {
        PersonDTO dto = new PersonDTO();
//        dto.setFirstName(person.getFirstName());
//        dto.setLastName(person.getLastName());
//        dto.setAddress(person.getAddress());
        return dto;
    }

    public static List<PersonDTO> modeldtoList(List<Person> persons) {
        return persons.stream().map(PersonNameMapper::toNameDTO).toList();
    }

}
