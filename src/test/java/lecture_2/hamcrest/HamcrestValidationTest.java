package lecture_2.hamcrest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HamcrestValidationTest {

    @Test
    public void jsonTest() {
        Response response = given()
                .when()
                .get("https://api.github.com/repos/SeleniumHQ/selenium");


        response.then()
                .body("id", Matchers.greaterThan(6))
                .body("name", Matchers.not(Matchers.empty()))
                .body("full_name", Matchers.containsString("SeleniumHQ"))
                .body("owner.site_admin", Matchers.is(Matchers.not(true)))
//                .body("full_name", Matchers.equalToIgnoringCase("seleniumhq"))
                .body("language", Matchers.anyOf(
                        Matchers.equalToIgnoringCase("java"),
                        Matchers.equalToIgnoringCase("python")

                ));
    }

    @Test
    public void hamcrestValidation() {
        given()
                .baseUri("https://bookstore.toolsqa.com")
                .when()
                .basePath("/BookStore/v1")
                .get("/Books")
                .then()
                .log().ifStatusCodeIsEqualTo(200)
                .body("books.author",
                        contains("Richard E. Silverman",
                                "Addy Osmani",
                                "Glenn Block et al.",
                                "Axel Rauschmayer",
                                "Kyle Simpson",
                                "Eric Elliott",
                                "Marijn Haverbeke",
                                "Nicholas C. Zakas")
                );

    }

    @Test
    public void hamcrestValidationAnyOrder() {
        given()
                .baseUri("https://bookstore.toolsqa.com")
                .when()
                .basePath("/BookStore/v1")
                .get("/Books")
                .then()
                .log().ifStatusCodeIsEqualTo(200)
                .body("books.author",
                        containsInAnyOrder(
                                "Marijn Haverbeke",
                                "Axel Rauschmayer",
                                "Richard E. Silverman",
                                "Addy Osmani",
                                "Glenn Block et al.",
                                "Kyle Simpson",
                                "Eric Elliott",
                                "Nicholas C. Zakas")
                );

    }

    @Test
    public void testValidation() {
        // Set base URI
        RestAssured.baseURI = "https://api.github.com";

        // Perform GET request
        Response response = given()
                .when()
                .get("/repos/SeleniumHQ/selenium")
                .then()
                .extract()
                .response();

        // Perform Hamcrest validations
        response.then()
                .body("id", greaterThan(0))
                .body("name", not(empty()))
                .body("full_name", containsString("SeleniumHQ"))
                .body("private", is(not(true)))
                .body("owner.login", equalToIgnoringCase("seleniumhq"))
                .body("owner.id", greaterThan(0))
                .body("owner.avatar_url", endsWith("v=4"))
                .body("description", containsStringIgnoringCase("browser automation"))
                .body("fork", is(not(true)))
                .body("url", matchesPattern("https://api\\.github\\.com/repos/SeleniumHQ/selenium"))
                .body("stargazers_count", greaterThanOrEqualTo(0))
                .body("watchers_count", greaterThanOrEqualTo(0))
                .body("language", anyOf(equalToIgnoringCase("java"), equalToIgnoringCase("javascript")))
                .body("has_issues", is(true))
                .body("has_projects", is(true))
                .body("has_downloads", is(true))
                .body("has_wiki", is(true))
                .body("has_pages", is(true))
                .body("forks_count", greaterThanOrEqualTo(0))
                .body("archived", is(not(true)))
                .body("open_issues_count", greaterThanOrEqualTo(0))
                .body("license.key", equalToIgnoringCase("apache-2.0"))
                .body("license.name", equalToIgnoringCase("Apache License 2.0"))
                .body("license.spdx_id", equalToIgnoringCase("Apache-2.0"))
                .body("license.url", containsString("github.com/licenses/apache-2.0"))
                .body("visibility", equalToIgnoringCase("public"))
                .body("default_branch", equalToIgnoringCase("trunk"))
                .body("organization.login", equalToIgnoringCase("seleniumhq"))
                .body("organization.id", greaterThan(0))
                .body("network_count", greaterThanOrEqualTo(0))
                .body("subscribers_count", greaterThanOrEqualTo(0));
    }


    public String endpoint = "https://restful-booker.herokuapp.com/booking/1";

    //    https://qaautomation.expert/2023/10/15/assertion-of-json-in-rest-assured-using-hamcrest/
    @Test
    public void numberAssertions() {
        RestAssured.given().contentType(ContentType.JSON)
                .when().get(endpoint).then()
                .body("totalprice", equalTo(518));

        RestAssured.given().contentType(ContentType.JSON)
                .when().get(endpoint)
                .then().body("totalprice",greaterThan(100));

        RestAssured.given().contentType(ContentType.JSON)
                .when().get(endpoint)
                .then().body("totalprice",greaterThanOrEqualTo(50));

        RestAssured.given().contentType(ContentType.JSON)
                .when().get(endpoint)
                .then().body("totalprice",lessThan(1000));

        RestAssured.given().contentType(ContentType.JSON)
                .when().get(endpoint)
                .then().body("totalprice",lessThanOrEqualTo(1000));

    }


}
