package com.safetynetalerts.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "firestations")
public class Firestation {

    private String address;
    private int station;

}
