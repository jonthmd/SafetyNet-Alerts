package com.safetynetalerts.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.dto.POJODTO;
import com.safetynetalerts.api.model.POJO;
import com.safetynetalerts.api.model.Person;
import com.safetynetalerts.api.service.POJOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    @Autowired
    ObjectMapper mapper;

    public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        POJO data = mapper.readValue(new File("src/main/resources/data.json"), POJO.class);

        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        System.out.println(prettyJson);

        List<Person> persons = data.getPersons();
        List<POJODTO> pojodtoList = POJOService.pojodtoList(persons);

        for(POJODTO pojo : pojodtoList){
            System.out.println(pojo);
        }
    }
}
