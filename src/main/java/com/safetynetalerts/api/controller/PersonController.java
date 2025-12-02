package com.safetynetalerts.api.controller;

import com.safetynetalerts.api.dto.*;
import com.safetynetalerts.api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller with endpoints operations related to person.
 */
@Slf4j
@RestController
//@RequestMapping("/person")
@Tag(name = "Persons", description = "Persons list.")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Retrieves all persons.
     * @return A list of PersonDTO representing all persons.
     */
    @GetMapping("/person/all")
    @Operation(summary = "Get all persons.", description = "Returns the complete list of persons recorded in the JSON.")
    public List<PersonDTO> getAllPersons() {
            log.info("Start getAllPersons() process...");
            return personService.getAll();
    }

    /**
     * Retrieves a specific person.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @return PersonDTO representing the person found.
     */
    @GetMapping("/person/{firstName}/{lastName}")
    @Operation(summary = "Get a person.", description = "Returns a person by entering firstname and lastname.")
    public PersonDTO getPerson(@PathVariable String firstName, @PathVariable String lastName) {
            log.info("Start getPerson() process...");
            return personService.getByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Retrieves children living at the address.
     * @param address The specified address.
     * @return PersonChildAlertDTO, a list of children and a list of family members.
     */
    @GetMapping("/childAlert")
    @Operation(summary = "Get children.", description = "Returns the complete list of children covered by address.")
    public PersonChildAlertDTO getChildrenByAddress(@RequestParam String address) {
            log.info("Start getChildrenByAddress() process...");
            return personService.getChildren(address);
    }

    /**
     * Retrieves information of persons with the same last name.
     * @param lastName The specified last name.
     * @return PersonInfoLastNameDTO, the detailed persons information.
     */
    @GetMapping("/personInfo")
    @Operation(summary = "Get persons infos.", description = "Returns info by entering lastName.")
    public PersonInfoLastNameDTO getPersonInfo(@RequestParam String lastName) {
            log.info("Start getPersonInfo() process...");
            return personService.getInfoLastName(lastName);
    }

    /**
     * Retrieves all emails of persons living at the specified city.
     * @param city The specified city.
     * @return PersonEmailDTO, a list of emails.
     */
    @GetMapping("/communityEmail")
    @Operation(summary = "Get emails.", description = "Returns persons emails by entering city.")
    public PersonEmailDTO getPersonsEmail(@RequestParam String city) {
            log.info("Start getPersonsEmail() process...");
            return personService.getEmails(city);
    }

    /**
     * Creates a new person.
     * @param personDTO The information of the person to create.
     */
    @PostMapping("/person")
    @Operation(summary = "Add a person.", description = "Adding a person by using firstname, lastname, address, city, zip, phone and email.")
    public void addPerson(@RequestBody PersonDTO personDTO) {
            log.info("Start addPerson() process...");
            personService.create(personDTO);
    }

    /**
     * Updates an existing person.
     * @param firstName The first name of the existing person.
     * @param lastName The last name of the existing person.
     * @param personDTO The updated person information.
     */
    @PutMapping("/person/{firstName}/{lastName}")
    @Operation(summary = "Edit a person.", description = "Edit a person, except firstname and lastname.")
    public void updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody PersonDTO personDTO) {
            log.info("Start updatePerson() process...");
            personService.update(firstName, lastName, personDTO);
    }

    /**
     * Deletes an existing person.
     * @param firstName The first name of the existing person.
     * @param lastName The last name of the existing person.
     */
    @DeleteMapping("/person/{firstName}/{lastName}")
    @Operation(summary = "Delete a person.", description = "Delete a person by using firstname and lastname.")
    public void deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
            log.info("Start deletePerson() process...");
            personService.delete(firstName, lastName);
    }
}
