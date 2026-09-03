package lecture_1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class ParameterCallTest {

    @Test
    public void restCallWithPathParameters() {
        HashMap<String, String> map = new HashMap<>();
        map.put("owner", "SeleniumHQ");
        map.put("repo", "selenium");


        ValidatableResponse response = given()
                .pathParams(map)
                .baseUri("https://api.github.com/")
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("/repos/{owner}/{repo}")
                .then()
                .log().body()
                .statusCode(200);

        String jsonString = response.extract().body().asString();
        System.out.println(jsonString);
    }

    @Test
    public void searchWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in";

        Response response = given()
                .log().all()
                .basePath("/api/users")
                .queryParam("page", 10)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get();

        response.then()
                .log().all()
                .statusCode(200)
//                .time(Matchers.lessThan(2000L))
                .time(Matchers.lessThan(2L), TimeUnit.SECONDS)
//              soft-assertion example
                .body("total_pages", Matchers.is(2),
                        "per_page", Matchers.is(6),
                        "support.text", Matchers.is("To keep ReqRes free, contributions towards server costs are appreciated!"))

//              hard-assertion example
                .body("total_pages", Matchers.is(2))
                .body("per_page", Matchers.is(6))
                .body("support.text", Matchers.is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void sendRequestWithJsonBody() {

        RestAssured.baseURI = "https://reqres.in";
        String jsonRequest = """
                {
                    "name": "morpheus",
                    "job": "leader"
                }
                """;
        Response response = given()
                .log().all()
                .basePath("/api/users")
                .contentType("application/json")
                .accept("application/json")
                .when()
                .body(jsonRequest)
                .post();

        response.then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .time(Matchers.lessThan(2L), TimeUnit.SECONDS)
                .body(
                        "name", Matchers.is("morpheus"),
                        "job", Matchers.equalTo("leader"),
                        "id", Matchers.notNullValue(),
                        "createdAt", Matchers.notNullValue()
                );
    }

    @Test
    public void sendRequestWithStringJsonBody() {

        RestAssured.baseURI = "https://reqres.in";

        String name = "morpheus";
        String job = "leader";

        // Using String formatting to create the JSON body with parameters
        String jsonRequest = """
                {
                    "name": "%s",
                    "job": "%s"
                }
                """.formatted(name, job);

        Response response = given()
                .log().all()
                .basePath("/api/users")
                .contentType("application/json")
                .accept("application/json")
                .when()
                .body(jsonRequest)
                .post();

        response.then()
                .log().all()
                .assertThat()
                .statusCode(201);
    }
}
