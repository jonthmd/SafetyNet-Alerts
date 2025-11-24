package com.safetynetalerts.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMedicalRecord() throws Exception {
        mockMvc.perform(get("/medicalRecord/{firstName}/{lastName}", "Felicia", "Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddMedicalRecord() throws Exception {
        String json = """
                  {
                      "firstName": "Jon",
                      "lastName": "TH",
                      "birthdate": "01/08/1986",
                      "medications": [
                        "tetracyclaz:650mg"
                      ],
                      "allergies": [
                        "xilliathal"
                      ]
                    }
                """;
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateMedicalRecord() throws Exception {
        String json = """
                  {
                      "firstName": "Jon",
                      "lastName": "TH",
                      "birthdate": "01/08/1986",
                      "medications": [
                        "tetracyclaz:650mg"
                      ],
                      "allergies": [
                        "xilliathal"
                      ]
                    }
                """;
        mockMvc.perform(put("/medicalRecord/{firstName}/{lastName}", "Jon", "TH", json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}", "Jon", "TH"))
                .andExpect(status().isOk());
    }

}