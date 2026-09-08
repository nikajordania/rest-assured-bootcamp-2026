package lecture_3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Oauth2AuthorizationTest {
    private static final String BASE_URI = "https://api.github.com";
    private static String TOKEN;
    private static String TOKEN_WITH_DELETE_ACCESS;
    private static final String USERNAME = "github-username";
    private static final String REPO_NAME = "NewRepo";

    @BeforeClass
    public void setUp() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            }
            properties.load(input);
            TOKEN = properties.getProperty("github.api.key");
            TOKEN_WITH_DELETE_ACCESS = properties.getProperty("github.delete.api.key");
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }

    }

    @Test
    public void createRepository() {
        RestAssured.baseURI = BASE_URI;

        Response response = given()
                .auth()
                .oauth2(TOKEN)
                .body("{\"name\": \"" + REPO_NAME + "\"}")
                .when()
                .post("/user/repos")
                .then()
                .statusCode(201)
                .extract()
                .response();

        response.then().body("name", equalTo(REPO_NAME));
        response.then().body("owner.login", equalTo(USERNAME));
    }
}
