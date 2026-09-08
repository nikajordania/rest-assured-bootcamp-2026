package lecture_3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.model.Fruit;
import data.model.booking.BookingResponseItem;
import data.model.f1.DriversItem;
import data.model.f1.DriversResponse;
import data.model.reqres.ListUsers.ListUsersResponse;
import data.model.reqres.User.UserResponse;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class DeserializationTest {
    @Test
    public void deserializeFruitFromJson() throws IOException {
        String json = "{\"name\":\"Apple\",\"id\":101}";
        ObjectMapper mapper = new ObjectMapper();

        Fruit fruit = mapper.readValue(json, Fruit.class);
        assertEquals(new Fruit("Apple", 101), fruit);
    }

    @Test
    public void getResource() {
        UserResponse userResponse = given()
                .baseUri("https://reqres.in/api/users/2")
                .header("x-api-key", "reqres-free-v1")
                .get()
                .then()
                .log().all().extract().body().as(UserResponse.class);


        System.out.println("userResponse.getData().getEmail() = " + userResponse.getData().getEmail());
    }

    @Test
    public void getUsersListFromAPI() {
        ListUsersResponse usersResponse = given()
                .baseUri("https://reqres.in/api")
                .header("x-api-key", "reqres-free-v1")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .and()
                .extract().body().as(ListUsersResponse.class);

        usersResponse.getData().forEach(System.out::println);
    }

    @Test
    public void deserializeDriversDataFromAPI() {
        Response response = given().when().get("https://api.jolpi.ca/ergast/f1/2025/drivers");

        DriversResponse driversResponse = response.then().extract().body().as(DriversResponse.class);


        DriversItem driversItem = driversResponse.getMRData().getDriverTable().getDrivers().get(0);

        Assert.assertEquals(driversItem.getDriverId(), "albon");
        Assert.assertEquals(driversItem.getGivenName(), "albon");



        System.out.println(driversResponse);

        driversResponse.getMRData().getDriverTable().getDrivers().forEach(System.out::println);
    }

    @Test
    public void extractPartOfJsonAsObject() {
        Response response = given().when().get("https://api.jolpi.ca/ergast/f1/2025/drivers");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());

        List<DriversItem> drivers = jsonPath.getList("MRData.DriverTable.Drivers", DriversItem.class);

        for (DriversItem driver : drivers) {
            System.out.println("Driver: " + driver.getGivenName() + " " + driver.getFamilyName());
            System.out.println("Nationality: " + driver.getNationality());
            System.out.println("Date of Birth: " + driver.getDateOfBirth());
            System.out.println("--------------");
        }
    }

    @Test
    public void deserializeBookingListFromAPI() {
        Response response = given().when().get("https://restful-booker.herokuapp.com/booking");

        List<BookingResponseItem> bookingResponseItems = response.then().extract().body().jsonPath().getList("$", BookingResponseItem.class);

        System.out.println(bookingResponseItems);
    }

    @Test
    public void deserializeBookingListUsingObjectMapper() throws JsonProcessingException {
        Response response = given().when().get("https://restful-booker.herokuapp.com/booking");

        ObjectMapper objectMapper = new ObjectMapper();
        List<BookingResponseItem> bookingResponseItems = objectMapper.readValue(response.then().extract().body().asString(), new TypeReference<List<BookingResponseItem>>() {
        });

        System.out.println(bookingResponseItems);
    }


}