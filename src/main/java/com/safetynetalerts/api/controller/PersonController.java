package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/person")
@Tag(name = "persons", description = "Persons list.")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "Get all persons.", description = "Returns the complete list of persons recorded in the JSON.")
    public List<PersonDTO> getAll(){
        return personService.getAll();
    }

    @GetMapping("/{firstName}/{lastName}")
    @Operation(summary = "Get a person.", description = "Returns a person by entering firstname and lastname.")
    public PersonDTO getPerson(@PathVariable String firstName, @PathVariable String lastName){
        return personService.getByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping
    @Operation(summary = "Add a person.", description = "Adding a person by using firstname, lastname, address, city, zip, phone and email.")
    public void addPerson(@RequestBody PersonDTO personDTO){
        personService.create(personDTO);
    }

    @PutMapping("/{firstName}/{lastName}")
    @Operation(summary = "Edit a person.", description = "Edit a person, except his firstname and lastname.")
    public void updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody PersonDTO personDTO){
        personService.update(firstName, lastName, personDTO);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    @Operation(summary = "Delete a person.", description = "Delete a person by using firstname and lastname.")
    public void deletePerson(@PathVariable String firstName, @PathVariable String lastName){
        personService.delete(firstName, lastName);
    }

}
