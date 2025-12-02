package com.safetynetalerts.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model representing a fire station saved in the JSON.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStation {

    private String address;
    private String station;

}
