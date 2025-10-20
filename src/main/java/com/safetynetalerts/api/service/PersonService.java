package com.safetynetalerts.api.service;

import com.safetynetalerts.api.model.Person;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonService {

    //TO DO

    @Autowired
    private JsonService jsonService;

    public Person addPerson(final Long id){
        return null;
    }

}
