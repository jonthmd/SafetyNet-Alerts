package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO used to represent a list of children and a list of their family members.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonChildAlertDTO {

    private List<PersonChildDTO> childDTOList;
    private List<PersonFamilyDTO> familyDTOList;

}
