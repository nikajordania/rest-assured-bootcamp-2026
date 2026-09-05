package lecture_2.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RequestAndResponseSpecificationTest {
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io")
                .setBasePath("/v2")
                .setContentType(ContentType.JSON)
                .build().log().all();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody(not(empty()))
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    public void testFindPetsByStatus() {
        Response response = given()
                .spec(requestSpec)
                .param("status", "available")
                .get("/pet/findByStatus");

        response.then()
                .spec(responseSpec)
                .body("status", everyItem(equalTo("available")));
    }

    @Test
    public void testFindPetById() {
        Response response = given()
                .spec(requestSpec)
                .get("/pet/10");

        response.then()
                .spec(responseSpec)
                .body("id", equalTo(10));
    }

    @Test
    public void testGetStoreInventory() {
        Response response = given()
                .spec(requestSpec)
                .get("/store/inventory");

        response.then()
                .spec(responseSpec)
                .body("totvs", is(4));
    }
}