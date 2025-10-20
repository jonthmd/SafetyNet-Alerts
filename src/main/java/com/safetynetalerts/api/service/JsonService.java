package com.safetynetalerts.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonService {

    @Autowired
    ObjectMapper mapper;

    public JsonData read() throws IOException {
        JsonData data = mapper.readValue(new File("src/main/resources/data.json"), JsonData.class);

        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        System.out.println(prettyJson);
        return data;
    }
}
