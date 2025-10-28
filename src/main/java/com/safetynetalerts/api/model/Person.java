package com.safetynetalerts.api.model;

import lombok.Data;

@Data
public class Person {

    //test rajout id
//    private Long id;

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
