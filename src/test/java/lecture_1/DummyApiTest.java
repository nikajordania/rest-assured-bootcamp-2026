package lecture_1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;

public class DummyApiTest {

    private static final String APP_ID = ""; // your application key
    private static String userId;

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://dummyapi.io/data/v1";
    }

    @Test
    public void getUserListNoAuth() {
        given()
                .when()
                .get("/")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getUserList() {
        given()
                .header("app-id", APP_ID)
                .when()
                .get("/user")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void pagination() {
        given()
                .header("app-id", APP_ID)
                .queryParam("page", 1)
                .queryParam("limit", 50)
                .when()
                .get("/user")
                .then()
                .assertThat()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("limit", equalTo(50))
                .body("data", hasSize(50))
                .body("data[0].id", matchesPattern("^[a-fA-F0-9]{24}$"));
    }

    @Test
    public void createUser() {
        String email = "auto.qa." + System.currentTimeMillis() + "@example.com";
        String body = """
                {
                    "firstName": "AutoUserName",
                    "lastName": "QA",
                    "email": "%s"
                }
                """.formatted(email);

        Response response = given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstName", equalTo("AutoUserName"))
                .body("lastName", equalTo("QA"))
                .extract().response();

        userId = response.jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "createUser")
    public void getUserById() {
        given()
                .header("app-id", APP_ID)
                .pathParam("userId", userId)
                .when()
                .get("/user/{userId}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(userId));
    }

    @Test(dependsOnMethods = "getUserById")
    public void deleteUserById() {
        given()
                .header("app-id", APP_ID)
                .pathParam("userId", userId)
                .when()
                .delete("/user/{userId}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(userId));
    }
}
