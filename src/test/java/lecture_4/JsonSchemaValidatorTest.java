package lecture_4;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.AssertJUnit.assertNotNull;

public class JsonSchemaValidatorTest {
    @Test
    public void
    validates_schema_in_classpath() {
        // Given
        String greetingJson = """
                {
                    "greeting": {
                        "firstName": "John",
                        "lastName": "Doe"
                    }
                }""";

        // Then
        assertThat(greetingJson, matchesJsonSchemaInClasspath("greeting-schema.json"));
    }

    @Test
    public void resp() {
        // Given
        String greetingJson = """
                {
                    "greeting": {
                        "firstName": "John",
                        "lastName": "Doe"
                    }
                }""";

        // Then
        given().when().get("/your_endpoint").then().assertThat().body(matchesJsonSchemaInClasspath("greeting-schema.json"));

    }

    @Test
    public void petStoreResponse() {
        given().when().get("https://petstore.swagger.io/v2/pet/10")
                .then().log().all()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("pet-schema.json"));


    }

    @Test
    public void petStoreResponseXMLSimple() {
        given().accept(ContentType.XML).when().get("https://petstore.swagger.io/v2/pet/10")
                .then().assertThat()
                .log().all()
                .body(matchesXsdInClasspath("schema.xsd"));


    }

    @Test
    public void petStoreResponseSimple() {
        given().when().get("https://petstore.swagger.io/v2/pet/10")
                .then().assertThat()
                .body(matchesJsonSchemaInClasspath("pet_schema_simple.json"));


    }

    @Test
    public void givenUrl_whenValidatesResponseWithInstanceSettings_thenCorrect() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
                .freeze();
        get("/events?id=390").then().assertThat()
                .body(matchesJsonSchemaInClasspath("event_0.json")
                        .using(jsonSchemaFactory));
    }

    @Test
    public void validateUserDetailsJsonSchema() {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri("https://gorest.co.in/public-api/")
                .when()
                .get("users/2")
                .then()
                .log().all();

        System.out.println("GET Response:\n" + response.extract().body().asString());

        Assert.assertEquals(response.extract().statusCode(), 200);

        assertNotNull(response.extract().body().jsonPath().get("data.id"));
        assertNotNull(response.extract().body().jsonPath().get("data.name"));
        assertNotNull(response.extract().body().jsonPath().get("data.email"));

        assertThat(response.extract().body().asString(), matchesJsonSchemaInClasspath("user-info.json"));

    }
}