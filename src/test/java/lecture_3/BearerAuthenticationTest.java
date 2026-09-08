package lecture_3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BearerAuthenticationTest {

    @Test
    public void bearerAuthTest() {
        String jwtToken = given()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"user\", \"password\": \"password\" }")
                .when()
                .post("https://api.example.com/auth/login")
                .then()
                .extract()
                .path("token");

        given()
                .header("Authorization", "Bearer " + jwtToken)
                .when()
                .get("/protected/resource")
                .then()
                .statusCode(200);
    }

    @Test
    public void bearerAuthTest2() {
        String requestBody = """
                {
                  "firstname": "nika",
                  "lastname": "zhordania",
                  "email": "test@gmail.com",
                  "password": "Qwerty123$",
                  "role": "ADMIN"
                }""";

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(200);

        String accessToken = response.extract().body().jsonPath().getString("access_token");

    }

    @Test
    public void getResourceTest() {
        RestAssured.baseURI = "http://localhost:8086";

        String requestBody = """
                {
                  "email": "test@gmail.com",
                  "password": "Qwerty123$"
                }""";

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/auth/authenticate")
                .then()
                .statusCode(200);

        String accessToken = response.extract().body().jsonPath().getString("access_token");


        given()
                .accept(ContentType.ANY)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/v1/admin/resource")
                .then()
                .statusCode(200)
                .log().all();

    }

}
