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
class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllFireStations() throws Exception {
        mockMvc.perform(get("/fireStation/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFireStation() throws Exception {
        mockMvc.perform(get("/fireStation/{address}", "1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRecordsPersons() throws Exception {
        mockMvc.perform(get("/fireStation/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPersonsByStationNumber() throws Exception {
        mockMvc.perform(get("/fireStation").param("stationNumber", "3"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddFireStation() throws Exception {
        String json = """
                {
                      "address": "1509 Culver St",
                      "station": "3"
                    }
                """;
        mockMvc.perform(post("/fireStation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateFireStation() throws Exception {
        String json = """
                {
                      "address": "1509 Culver St",
                      "station": "3"
                    }
                """;
        mockMvc.perform(put("/fireStation/{address}", "509 r")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteFireStation() throws Exception {
        mockMvc.perform(delete("/fireStation/{address}", "509 r"))
                .andExpect(status().isOk());
    }
}