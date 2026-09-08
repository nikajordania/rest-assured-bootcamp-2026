package lecture_3;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BasicAuthorizationTest {
    @Test
    public void testAuthorizedAccess() {
        given()
                .log().all()
                .auth()
                .preemptive()
                .basic("postman", "postman")
                .when()
                .get("https://postman-echo.com/get")
                .then()
                .statusCode(200)
                .log().all();
    }
}
