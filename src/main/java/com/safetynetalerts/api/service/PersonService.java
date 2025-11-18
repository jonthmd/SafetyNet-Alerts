package com.safetynetalerts.api.service;

import com.safetynetalerts.api.dto.PersonChildAlertDTO;
import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.dto.PersonPhoneAlertDTO;

import java.util.List;

public interface PersonService {

    List<PersonDTO> getAll();
    PersonDTO getByFirstNameAndLastName(String firstName, String lastName);
    PersonChildAlertDTO getChildren(String address);
    PersonPhoneAlertDTO getPhones(String stationNumber);
    PersonDTO create(PersonDTO personDTO);
    PersonDTO update(String firstName, String lastName, PersonDTO personDTO);
    void delete(String firstName, String lastName);

}
