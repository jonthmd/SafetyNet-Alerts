package com.safetynetalerts.api.service.implementation;

import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.mapper.PersonMapper;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.repository.DataRepository;
import com.safetynetalerts.api.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServiceImpl implements PersonService {

    private final DataRepository dataRepository;
    private final PersonMapper personMapper;

    public PersonServiceImpl(DataRepository dataRepository, PersonMapper personMapper) {
        this.dataRepository = dataRepository;
        this.personMapper = personMapper;
    }


    @Override
    public List<PersonDTO> getAll() {
        return dataRepository.getPersons()
                .stream()
                .map(personMapper::personToPersonDto)
                .toList();
    }

    @Override
    public PersonDTO getByFirstNameAndLastName(String firstName, String lastName) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                .map(personMapper::personToPersonDto)
                .findFirst()
                .orElse(null);
    }

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        Person person = personMapper.personDtoToPerson(personDTO);
        dataRepository.getPersons().add(person);
        return personMapper.personToPersonDto(person);
    }

    @Override
    public PersonDTO update(String firstName, String lastName, PersonDTO personDTO) {
        return dataRepository.getPersons()
                .stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .map(current -> {
                    current.setAddress(personDTO.getAddress());
                    current.setCity(personDTO.getCity());
                    current.setZip(personDTO.getZip());
                    current.setPhone(personDTO.getPhone());
                    current.setEmail(personDTO.getEmail());
                    return personMapper.personToPersonDto(current);
                })
                .orElse(null);
    }

    @Override
    public void delete(String firstName, String lastName) {
        dataRepository.getPersons()
                .removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName));
    }
}
