package com.safetynetalerts.api.repository;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.JsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class PersonRepository {

    @Autowired
    private JsonService jsonService;

    public List<Person> getAll(){

        if (jsonService.getData() == null || jsonService.getData().getPersons() == null) {
            log.debug("Nothing!");
        }

        return jsonService.getData().getPersons();
    }

    public void addPerson(Person person){
        getAll().add(person);
    }

    public void updatePerson(Person updated){
        for (Person current : getAll()){
            if (current.getFirstName().equals(updated.getFirstName()) && current.getLastName().equals(updated.getLastName())){
                current.setAddress(updated.getAddress());
                current.setCity(updated.getCity());
                current.setZip(updated.getZip());
                current.setPhone(updated.getPhone());
                current.setEmail(updated.getEmail());
            }
        }
    }

    public void deletePerson(String firstName, String lastName){
        getAll().removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
    }

}
