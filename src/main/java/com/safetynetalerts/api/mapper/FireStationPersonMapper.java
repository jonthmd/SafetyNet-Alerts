package com.safetynetalerts.api.mapper;

import com.safetynetalerts.api.dto.FireStationPersonDTO;
import com.safetynetalerts.api.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FireStationPersonMapper {

    FireStationPersonDTO fireStationPersonToFireStationPersonDto (Person person);
}
