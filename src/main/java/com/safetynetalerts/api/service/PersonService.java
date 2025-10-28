package com.safetynetalerts.api.service;

import com.safetynetalerts.api.controller.PersonController;
import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.mapper.PersonUpdateMapper;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons(){
        return personRepository.getAll();
    }

    public Person getPerson(String firstName, String lastName){
       return personRepository.getPerson(firstName, lastName);
    }

    public void addPerson(Person person){
        personRepository.addPerson(person);
    }

    public void updatePerson(String firstName, String lastName, PersonDTO dto){
        Person person = personRepository.getPerson(firstName, lastName);
        PersonUpdateMapper.toUpdateDTO(person, dto);
        personRepository.updatePerson(person);
    }

    public void deletePerson(String firstName, String lastName){
        personRepository.deletePerson(firstName, lastName);
    }
}
