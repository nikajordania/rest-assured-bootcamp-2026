package lecture_2.json;

import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JsonBodyGenerationTest {
    Config config;

    @BeforeSuite
    public void setUp() {
        config = new Config();
    }

    @Test
    public void incorrectWayTest() {
        String requestBody = """
                {
                  "id": 10,
                  "name": "doggie",
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }""";

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("https://petstore3.swagger.io/api/v3/pet")
                .then()
                .statusCode(200);
    }

    //    Create JSON from a HashMap
//    https://github.com/rest-assured/rest-assured/wiki/Usage#create-json-from-a-hashmap
    @Test
    public void createJsonUsingHasMap() {
//        {
//            "name": "morpheus",
//                "job": "leader",
//        }
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "morpheus");
        jsonAsMap.put("job", "leader");

        given()
                .contentType(JSON)
                .header("x-api-key", config.getApiKey())
                .body(jsonAsMap)
                .log().all()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log()
                .all();
    }

    @Test
    public void testJson1() {
        JSONObject category = new JSONObject();
        category.put("id", 0);
        category.put("name", "string");

        JSONArray photoUrls = new JSONArray();
        photoUrls.put("string").put("asdsd");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 0);
        jsonObject.put("category", category);
        jsonObject.put("name", "doggie");

        System.out.println(jsonObject.toString());
    }

    @Test
    public static void testJson2() {
        JSONObject jsonObject = new JSONObject()
                .put("id", 0)
                .put("category", new JSONObject()
                        .put("id", 0)
                        .put("name", "string"))
//                .put("photoUrls", new String[]{"string"})
                .put("photoUrls", new JSONArray().put("string"));

        System.out.println(jsonObject.toString(4));
    }

    @Test
    public static void test3() {
        Faker faker = new Faker();

        JSONObject jsonObject = new JSONObject()
                .put("id", faker.number().randomNumber())
                .put("category", new JSONObject()
                        .put("id", faker.number().randomNumber())
                        .put("name", faker.animal().name()));

        System.out.println(jsonObject.toString(4));


    }

    //    https://api.jolpi.ca/ergast/
    @Test
    public void extractResponseAsJsonObjectString() {

        String response = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers/")
                .then()
                .assertThat()
                .body("MRData.DriverTable.Drivers.driverId[-1]", equalTo("max_verstappen")).extract().response().body().asString();

        JSONObject jsonResponse = new JSONObject(response);

        System.out.println("Response JSONObject: " + jsonResponse);

        JSONObject mrData = jsonResponse.getJSONObject("MRData");
        JSONObject driverTable = mrData.getJSONObject("DriverTable");
        JSONArray drivers = driverTable.getJSONArray("Drivers");

        for (int i = 0; i < drivers.length(); i++) {
            JSONObject driver = drivers.getJSONObject(i);
            String driverId = driver.getString("driverId");
            String givenName = driver.getString("givenName");
            String familyName = driver.getString("familyName");
            String nationality = driver.getString("nationality");

            System.out.println("Driver " + (i + 1) + ":");
            System.out.println("Driver ID: " + driverId);
            System.out.println("Given Name: " + givenName);
            System.out.println("Family Name: " + familyName);
            System.out.println("Nationality: " + nationality);
            System.out.println("----------------------");
        }
    }

    @Test
    public void extractResponseAsJsonObject() {

        String json = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers")
                .then()
                .assertThat()
                .body("MRData.DriverTable.Drivers.driverId[-1]", equalTo("max_verstappen")).extract().body().asString();

        JSONObject jsonResponse = new JSONObject(json);

        System.out.println("Response JSONObject: " + jsonResponse);

        JSONObject mrData = jsonResponse.getJSONObject("MRData");
        JSONObject driverTable = mrData.getJSONObject("DriverTable");
        JSONArray drivers = driverTable.getJSONArray("Drivers");

        JSONObject firstDriver = drivers.getJSONObject(0);

        System.out.println(firstDriver);
    }

    @Test
    public void extractResponseAsMapObject() {
        Map<String, Object> driverData = given()
                .when()
                .param("format", "json")
                .get("https://api.jolpi.ca/ergast/f1/2025/drivers")
                .then()
                .extract().jsonPath()
                .getJsonObject("MRData.DriverTable.Drivers[0]");

        String driverId = (String) driverData.get("driverId");
        String givenName = (String) driverData.get("givenName");
        String familyName = (String) driverData.get("familyName");

        System.out.println("Driver ID: " + driverId);
        System.out.println("Given Name: " + givenName);
        System.out.println("Family Name: " + familyName);
    }

    @Test
    public void jsonArray() {

        String string = given()
                .when()
                .get("https://api.restful-api.dev/objects")
                .then()
                .extract().body().asString();

        JSONArray jsonArray = new JSONArray(string);

        System.out.println(jsonArray.getJSONObject(0).opt("asdasd"));

    }

    @Test
    public void testObjectMapping() {
        String jsonBody = given()
                .contentType(JSON)
                .log().all()
                .when()
                .header("x-api-key", config.getApiKey())
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .log()
                .all().extract().body().asString();

        JSONObject jsonObject = new JSONObject(jsonBody);

        assertThat(jsonObject.getInt("page"), equalTo(2));
        assertThat(jsonObject.getInt("per_page"), equalTo(6));
        assertThat(jsonObject.getInt("total"), equalTo(12));
        assertThat(jsonObject.getInt("total_pages"), equalTo(2));

        JSONArray dataArray = jsonObject.getJSONArray("data");
        assertThat(dataArray.length(), equalTo(6));

        JSONObject firstDataObject = dataArray.getJSONObject(0);
        assertThat(firstDataObject.getInt("id"), equalTo(7));
        assertThat(firstDataObject.getString("email"), equalTo("michael.lawson@reqres.in"));
        assertThat(firstDataObject.getString("first_name"), equalTo("Michael"));
        assertThat(firstDataObject.getString("last_name"), equalTo("Lawson"));
        assertThat(firstDataObject.getString("avatar"), equalTo("https://reqres.in/img/faces/7-image.jpg"));

        JSONObject supportObject = jsonObject.getJSONObject("support");
        assertThat(supportObject.getString("url"), equalTo("https://benhowdle.im/first-cto-playbook?utm_source=reqres&utm_medium=json&utm_campaign=referral"));
        assertThat(supportObject.getString("text"), equalTo("Become a better CTO. A playbook of painful stories and practical advice from a two-time startup CTO."));
    }
}
