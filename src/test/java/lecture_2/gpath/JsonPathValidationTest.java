package lecture_2.gpath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.hamcrest.text.CharSequenceLength;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonPathValidationTest {
    @Test
    public void extractAndCheckSingleValue() {

        Response response = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers");


//        response.then()
//                .assertThat()
//                .rootPath("MRData.DriverTable.Drivers")
//                .body("driverId[0..2]", hasSize(3));

        response.prettyPrint();

        response.then().body("""
                    MRData.DriverTable.Drivers
                        .findAll { it.permanentNumber != null }
                        .findAll { it.permanentNumber.toInteger() >= 30 && it.permanentNumber.toInteger() <= 40 }
                        .collect { it.permanentNumber.toInteger() }
                        .sum()
                """, greaterThan(30));

    }

    @Test
    public void parseInt() {

        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Perform GET request with query parameters
        Response response = given()
                .queryParam("username", "nika")
                .queryParam("password", "123")
                .header("accept", "application/json")
                .get("/user/login")
                .then()
                .body("message.split(':')[1]",
                        allOf(
                                matchesRegex("\\d+"),
                                CharSequenceLength.hasLength(greaterThanOrEqualTo(10))
                        )
                )

                .extract().response();


        String message = (response.body().jsonPath().getString("message").replaceAll("[\\D]", ""));
//        \W  Match a non-word character
//        \D  Match a non-digit character
        String numericValue = response.body().jsonPath().getString("message").split(":")[1].trim();
        String sessionId = response.jsonPath().getString("message.split(':')[1]");

        assertThat(message.length(), Matchers.greaterThanOrEqualTo(10));
        assertThat(numericValue.length(), Matchers.greaterThanOrEqualTo(10));
        assertThat(sessionId.length(), Matchers.greaterThanOrEqualTo(10));
        System.out.println("message = " + message);
        System.out.println("numericValue = " + numericValue);
        System.out.println("sessionId = " + sessionId);
    }

}
