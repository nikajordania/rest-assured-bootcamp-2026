package lecture_2.files;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class FileDownloadExampleTest {

    @Test
    public static void downloadFileTest() {
        String fileUrl = "https://static.vecteezy.com/system/resources/thumbnails/025/181/412/small/picture-a-captivating-scene-of-a-tranquil-lake-at-sunset-ai-generative-photo.jpg";

        String savePath = "src/downloads/file.jpg";

        Response response = RestAssured.get(fileUrl).then().log().all().extract().response();

        if (response.getStatusCode() == 200) {
            try {
                FileUtils.copyInputStreamToFile(response.getBody().asInputStream(), new File(savePath));
                System.out.println("File downloaded successfully to: " + savePath);
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
            }
        } else {
            System.err.println("Failed to download file. Status code: " + response.getStatusCode());
        }
    }
}


