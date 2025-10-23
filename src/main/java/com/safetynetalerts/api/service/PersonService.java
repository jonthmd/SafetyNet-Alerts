package com.safetynetalerts.api.service;

import com.safetynetalerts.api.controller.PersonController;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons(){
        return personRepository.getAll();
    }

    public void addPerson(Person person){
        personRepository.addPerson(person);
    }

    public void updatePerson(Person person){
        personRepository.updatePerson(person);
    }

    public void deletePerson(String firstName, String lastName){
        personRepository.deletePerson(firstName, lastName);
    }
}
