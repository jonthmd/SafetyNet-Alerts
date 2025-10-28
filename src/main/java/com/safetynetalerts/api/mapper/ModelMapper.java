package com.safetynetalerts.api.mapper;


import com.safetynetalerts.api.dto.ModelDTO;
import com.safetynetalerts.api.model.Person;

import java.util.List;

public class ModelMapper {

    //tester mapstruct

    public static ModelDTO toDTO(Person person) {
        ModelDTO dto = new ModelDTO();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
//        dto.setAddress(person.getAddress());
        return dto;
    }

    public static List<ModelDTO> modeldtoList(List<Person> persons) {
        return persons.stream().map(ModelMapper::toDTO).toList();
    }

}
