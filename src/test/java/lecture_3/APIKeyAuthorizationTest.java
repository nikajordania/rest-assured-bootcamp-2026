package lecture_3;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class APIKeyAuthorizationTest {
    String openWeatherMapApiKey;

    @BeforeClass
    public void setUp() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/secret.properties"));

            openWeatherMapApiKey = properties.getProperty("openweathermap.api.key");

            System.out.println("OpenWeatherMap API Key: " + openWeatherMapApiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testWeatherAPI() {

        given()
                .queryParam("q", "Tbilisi")
                .queryParam("appid", openWeatherMapApiKey)
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .then()
                .statusCode(200)
                .log().all();
    }
}
