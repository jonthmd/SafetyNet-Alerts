package com.safetynetalerts.api.mapper;

import com.safetynetalerts.api.dto.PersonChildDTO;
import com.safetynetalerts.api.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonChildMapper {

    PersonChildDTO personChildToPersonChildDTO(Person person);

}
