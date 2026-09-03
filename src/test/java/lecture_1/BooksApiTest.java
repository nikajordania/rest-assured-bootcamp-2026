package lecture_1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BooksApiTest {
    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
    }

    @Test
    public void getBooks() {
        given()
                .when()
                .basePath("/BookStore/v1")
                .get("/Books")
                .then()
                .assertThat()
                .statusCode(200)
                .body("books[0].author", equalTo("Richard E. Silverman"));
    }

    @Test
    public void getBook() {
        given()
                .when()
                .basePath("/BookStore/v1")
                .get("/Book?ISBN=9781449325862")
                .then()
                .assertThat()
                .statusCode(200)
                .body("author", equalTo("Richard E. Silverman"));
    }

    @Test
    public void getBookWithParameter() {
        given()
                .param("ISBN", "9781449325862")
                .when()
                .basePath("/BookStore/v1")
                .get("/Book")
                .then()
                .assertThat()
                .statusCode(200)
                .body("author", equalTo("Richard E. Silverman"));
    }

    @Test
    public void getBookWithHashMapParameter() {
        Map<String, String> map = new HashMap<>();
        map.put("ISBN", "9781449325862");

        given()
                .params(map)
                .when()
                .basePath("/BookStore/v1")
                .get("/Book")
                .then()
                .assertThat()
                .statusCode(200)
                .body("author", equalTo("Richard E. Silverman"));
    }

    @Test
    public void getBookExtractJsonPathResults() {
        Map<String, String> map = new HashMap<>();
        map.put("ISBN", "9781449325862");

        Response response = given()
                .params(map)
                .when()
                .basePath("/BookStore/v1")
                .get("/Book")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("author", equalTo("Richard E. Silverman")).extract().response();

        String author = response.jsonPath().getString("author");
        System.out.println("author = " + author);

        String title = response.jsonPath().getString("title");
        System.out.println("title = " + title);

        String subTitle = response.jsonPath().getString("subTitle");
        System.out.println("subTitle = " + subTitle);

        String publishDate = response.jsonPath().getString("publish_date");
        System.out.println("publishDate = " + publishDate);

        int pages = response.jsonPath().getInt("pages");
        System.out.println("pages = " + pages);
    }
}