package com.safetynetalerts.api.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.model.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Data {

    @Autowired
    ObjectMapper mapper;

    private JsonData data;

    @Bean(initMethod = "")
    public JsonData read() throws IOException {
        data = mapper.readValue(new File("src/main/resources/data.json"), JsonData.class);
//        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
//        System.out.println(prettyJson);
        return data;
    }

    public JsonData getData(){
        return data;
    }
}
