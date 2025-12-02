package com.safetynetalerts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO used to represent a set of emails.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEmailDTO {

    private Set<String> email;

}
