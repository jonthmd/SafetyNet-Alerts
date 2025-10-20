package com.safetynetalerts.api.mapper;


import com.safetynetalerts.api.dto.modelDTO;
import com.safetynetalerts.api.model.Person;

import java.util.List;

public class modelMapper {

    public static modelDTO toDTO(Person person) {
        modelDTO dto = new modelDTO();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        return dto;
    }

    public static List<modelDTO> modeldtoList(List<Person> persons) {
        return persons.stream().map(modelMapper::toDTO).toList();
    }

}
