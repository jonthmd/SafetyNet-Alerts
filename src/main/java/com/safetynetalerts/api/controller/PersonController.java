package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return personService.getAllPersons();
    }

    @GetMapping("/person/{firstName}/{lastName}")
    @Operation(summary = "Get a person.", description = "Returns a person by entering firstname and lastname.")
    public Person getPerson(@PathVariable String firstName, @PathVariable String lastName){
        return personService.getPerson(firstName, lastName);
    }

    @PostMapping("/person")
    @Operation(summary = "Add a person.", description = "Adding a person by using firstname, lastname, address, city, zip, phone and email.")
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @PutMapping("/person/{firstName}/{lastName}")
    @Operation(summary = "Edit a person.", description = "Edit a person, except his firstname and lastname.")
    public void updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody PersonDTO dto){
        personService.updatePerson(firstName, lastName, dto);
    }

    @DeleteMapping("/person/{firstName}/{lastName}")
    @Operation(summary = "Delete a person.", description = "Delete a person by using firstname and lastname.")
    public void deletePerson(@PathVariable String firstName, @PathVariable String lastName){
        personService.deletePerson(firstName, lastName);
    }
}
