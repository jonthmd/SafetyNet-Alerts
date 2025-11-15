package com.safetynetalerts.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.api.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataRepositoryTest {

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private DataRepository dataRepository;

    String json = """
            {
            "persons": [{"firstName":"John","lastName":"Boyd","address":"1509 Culver St","city":"Culver","zip":"9745","phone":"841-874-6512","email":"jaboyd@email.com"}],
            "firestations": [{"address":"1509 Culver St","station":"3"}],
            "medicalrecords": [{"firstName":"John","lastName":"Boyd","birthdate":"03/06/1984","medications":["aznol:350mg"],"allergies":["nillacilan"]}]
            }
            """;

    String jsonNodeNull = "";


    @Test
    public void testInit() throws IOException {

        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        when(mapper.readTree(any(File.class))).thenReturn(rootNode);

        //WHEN
        dataRepository.init();

        //THEN
        assertThat(dataRepository.getPersons().size()).isEqualTo(1);
        assertThat(dataRepository.getFireStations().size()).isEqualTo(1);
        assertThat(dataRepository.getMedicalRecords().size()).isEqualTo(1);
    }

    @Test
    public void testInitNodeNull() throws IOException {

        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonNodeNull);
        when(mapper.readTree(any(File.class))).thenReturn(rootNode);

        //WHEN
        dataRepository.init();

        //THEN
        assertThat(dataRepository.getPersons()).isEmpty();
        assertThat(dataRepository.getFireStations()).isEmpty();
        assertThat(dataRepository.getMedicalRecords()).isEmpty();
    }

    @Test
    public void testInitExceptionHandled() throws IOException {

        //GIVEN
        when(mapper.readTree(any(File.class))).thenThrow(new IOException("Error."));

        //WHEN + THEN
        assertThatThrownBy(() -> dataRepository.init())
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Error.");
    }

}
