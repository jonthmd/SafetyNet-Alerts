package com.safetynetalerts.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.dto.modelDTO;
import com.safetynetalerts.api.model.JsonData;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.mapper.modelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
