package lecture_3;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FormAuthTest {

    @Test
    public void testForm() {

        given()
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "simpleForm@authenticationtest.com")
                .formParam("password", "pa$$w0rd")
                .when()
                .post("https://authenticationtest.com/login/?mode=simpleFormAuth")
                .then()
                .log().all()
                .statusCode(302)
                .extract().response();

    }

    @Test
    public void testForm2() {
        given()
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "complex@authenticationtest.com")
                .formParam("password", "pa$$w0rd")
                .formParam("selectLogin", "yes")
                .formParam("loveForm", "on")
                .when()
                .post("https://authenticationtest.com/login/?mode=complexAuth")
                .then()
                .log().all()
                .extract().response();
    }

}
