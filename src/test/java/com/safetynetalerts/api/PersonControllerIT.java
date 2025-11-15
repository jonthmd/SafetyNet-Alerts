package com.safetynetalerts.api;

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
public class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAPerson() throws Exception {
        mockMvc.perform(get("/person/{firstName}/{lastName}", "Jon", "TH"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPerson() throws Exception {

        String json = """
            {
            "firstName":"Jon","lastName":"TH","address":"2r","city":"cm","zip":"92","phone":"841-874-6512","email":"jon@email.com"
            }
            """;

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePerson() throws Exception {

        String json = """
            {
            "firstName":"Jon","lastName":"TH","address":"2r","city":"cm","zip":"92","phone":"841-874-6512","email":"jon@email.com"
            }
            """;

        mockMvc.perform(put("/person/{firstName}/{lastName}", "Jon", "TH")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {

        String json = """
            {
            "firstName":"Jon","lastName":"TH","address":"2r","city":"cm","zip":"92","phone":"841-874-6512","email":"jon@email.com"
            }
            """;

        mockMvc.perform(delete("/person/{firstName}/{lastName}", "Jon", "TH")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}
