package com.safetynetalerts.api.service;

import com.safetynetalerts.api.dto.*;

import java.util.List;

public interface PersonService {

    List<PersonDTO> getAll();
    PersonDTO getByFirstNameAndLastName(String firstName, String lastName);
    PersonChildAlertDTO getChildren(String address);
    PersonPhoneAlertDTO getPhones(String stationNumber);
    PersonInfoLastNameDTO getInfoLastName(String lastName);
    PersonEmailDTO getEmails(String city);
    PersonDTO create(PersonDTO personDTO);
    PersonDTO update(String firstName, String lastName, PersonDTO personDTO);
    void delete(String firstName, String lastName);

}
