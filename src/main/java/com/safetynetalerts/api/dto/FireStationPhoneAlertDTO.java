package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO used to represent a set of phone numbers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationPhoneAlertDTO {

    private Set<String> phone;

}
