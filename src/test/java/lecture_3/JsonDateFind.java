package lecture_3;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class JsonDateFind {
    @Test
    public void jsonGetLastDate() throws IOException {
        String json = Files.readString(Path.of("src/main/resources/get-by-date.json"));

        System.out.println(json);

        JsonPath jsonPath = new JsonPath(json);

        List<Map<String, Object>> items = jsonPath.getList("$");

//        List<Map<String, Object>> sortedItems = items.stream()
//                .sorted((item1, item2) -> {
//                    String date1 = (String) item1.get("date");
//                    String date2 = (String) item2.get("date");
//                    return date2.compareTo(date1);
//                })
//                .toList();

        List<Map<String, Object>> sortedItems = items.stream()
                .sorted((item1, item2) -> {
                    LocalDateTime date1 = LocalDateTime.parse((CharSequence) item1.get("date"));
                    LocalDateTime date2 = LocalDateTime.parse((CharSequence) item2.get("date"));
                    return date2.compareTo(date1);
                })
                .toList();

        Map<String, Object> latestItem = sortedItems.get(0);

        System.out.println("Latest item:");
        System.out.println(latestItem);

    }

}
