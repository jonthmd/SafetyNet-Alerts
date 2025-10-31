package com.safetynetalerts.api.service;

import com.safetynetalerts.api.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    List<PersonDTO> getAll();
    PersonDTO getByFirstNameAndLastName(String firstName, String lastName);
    PersonDTO create(PersonDTO personDTO);
    PersonDTO update(String firstName, String lastName, PersonDTO personDTO);
    void delete(String firstName, String lastName);
}
