package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.JsonService;
import com.safetynetalerts.api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Data
@RestController
//@RequestMapping("/persons")
@Tag(name = "persons", description = "Persons list.")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/persons")
    @Operation(summary = "Get all persons.", description = "Returns the complete list of persons recorded in the JSON.")
    public List<Person> getAll(){
        log.debug("Found persons : {}", personService.getPersonRepository().getAll().size());
        return personService.getPersonRepository().getAll();
    }
}
