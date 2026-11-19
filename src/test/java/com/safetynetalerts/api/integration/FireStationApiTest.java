package com.safetynetalerts.api.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FireStationApiTest {

    @LocalServerPort
    private int port;

    private static final String BASE_URL = "http://localhost";
    private static final String ADDRESS1 = "1509 Culver St";
    private static final String ADDRESS2 = "5 allée Paul Éluard";

    @Test
    @Order(1)
    void shouldGetFireStationByAddress() {
        // GET
        given()
                .baseUri(BASE_URL)
                .port(port)
                .pathParam("address", ADDRESS1)
                .when()
                .get("/fireStation/{address}")
                .then()
                .statusCode(200)
                .body("address", equalTo(ADDRESS1));
    }

    @Test
    @Order(2)
    void shouldCreateFireStation() {
        // POST
        String address = "5 allée Paul Éluard";
        String station = "78";

        String body = """
                {
                    "address":"%s",
                    "station": "%s"
                }
                """.formatted(address, station);

        given()
                .baseUri(BASE_URL)
                .port(port)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/fireStation")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void shouldGetCreatedFireStation() {
        // GET
        given()
                .baseUri(BASE_URL)
                .port(port)
                .pathParam("address", ADDRESS2)
                .when()
                .get("/fireStation/{address}")
                .then()
                .statusCode(200)
                .body("address", equalTo(ADDRESS2),
                        "station", equalTo("78"));
    }

    @Test
    @Order(4)
    void shouldUpdateFireStation() {
        // PUT
        String address = "5 allée Paul Éluard";
        String station = "73";

        String body = """
                {
                    "address":"%s",
                    "station": "%s"
                }
                """.formatted(address, station);

        given()
                .baseUri(BASE_URL)
                .port(port)
                .contentType("application/json")
                .body(body)
                .pathParam("address", ADDRESS2)
                .when()
                .put("/fireStation/{address}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    void shouldGetEditedFireStation() {
        // GET
        given()
                .baseUri(BASE_URL)
                .port(port)
                .pathParam("address", ADDRESS2)
                .when()
                .get("/fireStation/{address}")
                .then()
                .statusCode(200)
                .body("address", equalTo(ADDRESS2),
                        "station", equalTo("73"));
    }

    @Test
    @Order(6)
    void shouldDeleteFireStation() {
        // DELETE
        given()
                .baseUri(BASE_URL)
                .port(port)
                .pathParam("address", ADDRESS2)
                .when()
                .delete("/fireStation/{address}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    void shouldNotGetDeletedFireStation() {
        // GET
        given()
                .baseUri(BASE_URL)
                .port(port)
                .pathParam("address", ADDRESS2)
                .when()
                .get("/fireStation/{address}")
                .then()
                .statusCode(404);
    }
}
