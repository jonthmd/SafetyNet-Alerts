package com.safetynetalerts.api.mapper;

import com.safetynetalerts.api.dto.PersonDTO;
import com.safetynetalerts.api.model.Person;

public class PersonUpdateMapper {

    public static void toUpdateDTO(Person person, PersonDTO dto){
        if (dto.getAddress() != null)
            person.setAddress(dto.getAddress());
        if (dto.getCity() != null)
            person.setCity(dto.getCity());
        if (dto.getZip() != null)
            person.setZip(dto.getZip());
        if (dto.getPhone() != null)
            person.setPhone(dto.getPhone());
        if (dto.getEmail() != null)
            person.setEmail(dto.getEmail());
    }

}
