package lecture_3;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class JwtBasedAuthTest {
    public void testJwtAuth() {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("""
                        {
                          "email": "n@mail.com",
                          "password": "Qwerty1!"
                        }""")
                .baseUri("http://localhost:8086")
                .when()
                .post("/api/v1/auth/authenticate")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("Response Body: " + response.asString());


        String jwtCookie = response.getCookie("jwt-cookie");
        String refreshJwtCookie = response.getCookie("refresh-jwt-cookie");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookie("jwt-cookie", jwtCookie)
                .baseUri("http://localhost:8086")
                .when()
                .get("/api/v1/admin/resource")
                .then()
                .statusCode(200)
                .log().body();


        Cookie jwt = response.getDetailedCookie("jwt-cookie");


        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .cookies(response.getDetailedCookies())
                .baseUri("http://localhost:8086")
                .when()
                .get("/api/v1/admin/resource")
                .then()
                .statusCode(200)
                .log().body();
    }
}
