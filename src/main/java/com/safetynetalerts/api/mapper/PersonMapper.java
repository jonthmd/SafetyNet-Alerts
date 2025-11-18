package com.safetynetalerts.api.mapper;

import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO personToPersonDto(Person person);
    Person personDtoToPerson(PersonDTO personDTO);

}
