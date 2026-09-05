package lecture_2.files;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.apache.commons.text.similarity.LevenshteinDistance.getDefaultInstance;

public class FileUploadTest {
    @Test
    public void testFileUpload() {
        File file = new File("src/main/resources/scan_img.png");

        given()
                .baseUri("http://localhost:8080/")
                .basePath("/v1")
//                .log().all()
                .accept(ContentType.ANY)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file, "image/png")
                .when()
                .post("/images/extract")
                .then()
                .log().all();

    }

    @Test
    public void testOCR() throws IOException {
        //        http://localhost:8000/docs
        File file = new File("src/main/resources/scan_img.png");

        String expectedText = Files.readString(Paths.get("src/main/resources/expected_text.txt"));
        System.out.println(expectedText);
        Response response = given()
                .baseUri("http://localhost:8080/")
                .basePath("/v1")
                .log().all()
                .accept(ContentType.ANY)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file, "image/png")
                .when()
                .post("/images/extract");

        String returnedText = response.body().asString();
        System.out.println(returnedText);

        LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
        int distance = levenshteinDistance.apply(expectedText, returnedText);

        double similarityPercentage = (1.0 - (double) distance / Math.max(expectedText.length(), returnedText.length())) * 100;
        System.out.println("Text similarity percentage: " + similarityPercentage + "%");
        Assert.assertTrue(similarityPercentage >= 95, "Text similarity is below 95%.");
    }
}
