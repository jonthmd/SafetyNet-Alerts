package com.safetynetalerts.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.safetynetalerts.api.model.FireStation;
import com.safetynetalerts.api.model.MedicalRecord;
import com.safetynetalerts.api.model.Person;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class DataRepository {

    public static String pathDataFile = "src/main/resources/data.json";
    private final ObjectMapper mapper;
    private JsonNode rootNode;

    @Getter
    private List<Person> persons;

    @Getter
    private List<FireStation> fireStations;

    @Getter
    private List<MedicalRecord> medicalRecords;


    public DataRepository(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() throws IOException {

        rootNode = mapper.readTree(new File(pathDataFile));

        persons = getObjectFromJsonNode("persons", Person.class);
        fireStations = getObjectFromJsonNode("firestations", FireStation.class);
        medicalRecords =  getObjectFromJsonNode("medicalrecords", MedicalRecord.class);
    }

    private <T> List<T> getObjectFromJsonNode(String nodeName, Class<T> tClass) {

        ObjectMapper privateMapper = new ObjectMapper();

        try {
            JsonNode node = rootNode.path(nodeName);

            if (node.isMissingNode() || node.isNull()) {
                return Collections.emptyList();
            }

            CollectionType collectionType = privateMapper.getTypeFactory().constructCollectionType(List.class, tClass);

            return privateMapper.readValue(node.traverse(), collectionType);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}
