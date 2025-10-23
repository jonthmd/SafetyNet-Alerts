package com.safetynetalerts.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.JsonData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonService {

    @Autowired
    ObjectMapper mapper;

    @Bean(initMethod = "")
    public JsonData read() throws IOException {
//        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
//        System.out.println(prettyJson);
        return mapper.readValue(new File("src/main/resources/data.json"), JsonData.class);
    }

    public JsonData getData(){
        return new JsonData();
    }
}
