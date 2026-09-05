package lecture_2.gpath;

import data.model.Driver;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Lecture2Test {

    //    https://restful-api.dev/
    @Test
    public void extractAndCheckSingleValue() {

        given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .assertThat()
                .body("MRData.DriverTable.Drivers.driverId[-1]", equalTo("russell"));
    }

    @Test
    public void extractAndCheckMultipleValues() {

        given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .assertThat()
                .body("MRData.DriverTable.Drivers.driverId", hasItems("alonso", "antonelli"));
    }

    @Test
    public void extractJson() {

        String json = """
                {
                    "message": "An entity of type topic was passed in an invalid format",
                    "meta": {
                        "display": {
                            "topic": [
                                {
                                    "name": [
                                        "must not be blank"
                                    ]
                                },
                                {
                                    "contentType": [
                                        "must not be blank"
                                    ]
                                },
                                {
                                    "content": [
                                        "must not be blank"
                                    ]
                                },
                                {
                                    "version": [
                                        "must not be blank"
                                    ]
                                }
                            ]
                        }
                    }
                }
                """;

        JsonPath jsonPath = new JsonPath(json);

        System.out.println(jsonPath.getString("meta.display.topic.find { it.contentType != null }.contentType[0]"));
        System.out.println(from(json).getString("meta.display.topic.find { it.contentType != null }.contentType[0]"));
    }

    @Test
    public void extractAndCheckMultipleValues2() {

        RestAssured.rootPath = "MRData.DriverTable.Drivers";

        given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .assertThat()
                .body("driverId", hasItems("alonso", "antonelli"));
    }

    @Test
    public void extractAndCheckMultipleValuesExample2() {

        given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .assertThat()
                .rootPath("MRData.DriverTable.Drivers")
                .body("driverId", hasItems("alonso", "antonelli"))

//                .detachRootPath("Drivers")
//                .detachRootPath("DriverTable.Drivers")
//                .noRootPath()
                .body("driverId", hasItems("alonso", "antonelli"));
    }

    @Test
    public void extractAndCheckArraySliceSize() {

        given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .assertThat()
                .rootPath("MRData.DriverTable.Drivers")
                .body("driverId[0..2]", hasSize(3));
    }

    @Test
    public void extractAndCheckRange() {

        ValidatableResponse response = given()
                .when()
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .statusCode(200)
                .contentType("application/json");

        List<Map<String, Object>> filteredDrivers =
                response.extract()
                        .jsonPath()
                        .getList("""
                            MRData.DriverTable.Drivers.findAll {
                                it.permanentNumber != null &&
                                it.permanentNumber.toInteger() >= 20 &&
                                it.permanentNumber.toInteger() <= 30
                            }
                            """);

        System.out.println("Drivers in range 20-30:");

        for (Map<String, Object> driver : filteredDrivers) {
            System.out.println("Driver: " + driver.get("givenName") + " " + driver.get("familyName") + " | Number: " + driver.get("permanentNumber"));
        }

        response
                .body("""
                    MRData.DriverTable.Drivers.findAll {
                        it.permanentNumber != null &&
                        it.permanentNumber.toInteger() >= 20 &&
                        it.permanentNumber.toInteger() <= 30
                    }.permanentNumber
                    """,
                        containsInAnyOrder("23", "27", "30"))

                .and()
                .body("""
                    MRData.DriverTable.Drivers.findAll {
                        it.permanentNumber != null &&
                        it.permanentNumber.toInteger() >= 20 &&
                        it.permanentNumber.toInteger() <= 30
                    }.permanentNumber
                    """,
                        not(hasItem("33")))

                .and()
                .body("""
                    MRData.DriverTable.Drivers.findAll {
                        it.permanentNumber != null &&
                        it.permanentNumber.toInteger() >= 20 &&
                        it.permanentNumber.toInteger() <= 30
                    }
                    .collect {
                        it.permanentNumber.toInteger()
                    }
                    """,
                        contains(23, 27, 30))
                .and()
                .body("MRData.DriverTable.Drivers.findAll { it.permanentNumber != null && it.permanentNumber.toInteger() >= 20 && it.permanentNumber.toInteger() <= 30 }.collect { it.permanentNumber.toInteger() }.sum()", equalTo(80));


        System.out.println(response.extract().jsonPath().setRootPath("MRData.DriverTable.Drivers")
                .getList("""
                        findAll { it.permanentNumber != null && it.permanentNumber.toInteger() >= 30 }
                        .collect { [driverId: it.driverId, permanentNumber: it.permanentNumber] }
                        """
                ));

        List<Driver> drivers = response.extract().jsonPath().setRootPath("MRData.DriverTable.Drivers")
                .getList("""
                        findAll { it.permanentNumber != null && it.permanentNumber.toInteger() >= 30 }
                        .collect { [driverId: it.driverId, permanentNumber: it.permanentNumber] }
                        """
                );
        System.out.println(drivers);

        List<Map<String, ?>> collectedMap = response.extract().jsonPath().setRootPath("MRData.DriverTable.Drivers")
                .getList("findAll { it.permanentNumber != null && it.permanentNumber.toInteger() >= 30 }.collect { [driverId: it.driverId, permanentNumber: it.permanentNumber] }");

        System.out.println(collectedMap);

        int anInt = response.extract().jsonPath().getInt("MRData.DriverTable.Drivers.findAll{ it.permanentNumber != null && it.permanentNumber.toInteger() >= 20 && it.permanentNumber.toInteger() <= 30 } .collect { it.permanentNumber.toInteger() } .sum()");

        System.out.println(anInt);

        assertThat(anInt, Matchers.greaterThanOrEqualTo(70));

    }

    @Test
    public void passParameter() {
        Response response = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/");


        System.out.println(response.jsonPath()
                .param("n1", 50)
                .setRootPath("MRData.DriverTable.Drivers")
                .getList("""
                        findAll { it.permanentNumber != null && it.permanentNumber.toInteger() >= n1 }
                        .collect { [driverId: it.driverId, permanentNumber: it.permanentNumber] }"""

                ));

    }

    @Test
    public void findByPassPath() {
        Response response = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/");


        System.out.println(Optional.ofNullable(response.jsonPath()
                .param("n1", 14)
                .setRootPath("MRData.DriverTable.Drivers")
                .getJsonObject("find { it.permanentNumber.toInteger() == n1 }"
                )));

    }

    @Test
    public void extractMaxAndMinPermanentNumberDriverId() {
        Response response = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/");


        String highestNumberDriverId = response.path("MRData.DriverTable.Drivers.max { it.permanentNumber != null && it.permanentNumber.toInteger() }.driverId");
        System.out.println(highestNumberDriverId);
        assertThat(highestNumberDriverId, Matchers.equalTo("albon"));
        String minNumberDriverId = response.path("MRData.DriverTable.Drivers.min { it.permanentNumber != null && it.permanentNumber.toInteger() }.driverId");
        System.out.println(minNumberDriverId);
    }
}
