package com.safetynetalerts.api.service;


import com.safetynetalerts.api.dto.POJODTO;
import com.safetynetalerts.api.model.Person;

import java.util.List;

public class POJOService {

    public static POJODTO toDTO(Person person) {
        POJODTO dto = new POJODTO();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        return dto;
    }

    public static List<POJODTO> pojodtoList(List<Person> persons) {
        return persons.stream().map(POJOService::toDTO).toList();
    }

}
