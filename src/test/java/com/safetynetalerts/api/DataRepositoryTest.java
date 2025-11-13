package com.safetynetalerts.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.safetynetalerts.api.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataRepositoryTest {

    //mocker les éléments externes
    @Mock
    private ObjectMapper mapper;

    @Mock
    private JsonNode tree;


    //class à tester
    DataRepository classUnderTest;

    @BeforeEach
    public void setup(){
        classUnderTest = new DataRepository(mapper);
    }

    @Test
    public void initTest() throws IOException {
        //GIVEN
        when(mapper.readTree(any(File.class))).thenReturn(tree);

        //WHEN
        classUnderTest.init();

        //THEN
        verify(mapper).readTree(any(File.class));
    }


}
