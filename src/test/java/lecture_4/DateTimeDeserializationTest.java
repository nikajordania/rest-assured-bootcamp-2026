package lecture_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.model.lecture_4.timeexample.DateTimeResponse;
import data.model.lecture_4.timeexample.EventWithLocalDate;
import data.model.lecture_4.timeexample.EventWithLocalDateTime;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class DateTimeDeserializationTest {

    @Test
    public void dateTimeTest() throws JsonProcessingException {
        String json = """
                {
                    "id": "10",
                    "name": "Apple iPad Mini 5th Gen",
                    "data": {
                      "Capacity": "64 GB",
                      "Screen size": 7.9
                    },
                    "date": "06-12-2022"
                  }
                """;

        ObjectMapper mapper = new ObjectMapper();

//        mapper.registerModule(new JavaTimeModule());

        DateTimeResponse response = mapper.readValue(json, DateTimeResponse.class);

        System.out.println(response.getDate());

        //serialization
        String serializedJson = mapper.writeValueAsString(response);
        System.out.println(serializedJson);
    }

    @Test
    public void timeTest() throws JsonProcessingException {

        String json = "{\"name\":\"party\",\"eventDate\":\"20-12-2014\",\"abc\":\"20-12-2014\"}";

        ObjectMapper mapper = new ObjectMapper();

        EventWithLocalDate result = mapper.readValue(json, EventWithLocalDate.class);
    }

    @Test
    public void dateTimeTest2() throws JsonProcessingException {

        String json = "{\"name\":\"party\",\"eventDate\":\"20-12-2014 15:33:00\"}";

        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());

        EventWithLocalDateTime result = mapper.readValue(json, EventWithLocalDateTime.class);
    }

    @Test
    public void dateTimeTestSer() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        EventWithLocalDateTime eventWithLocalDateTime = new EventWithLocalDateTime();

        eventWithLocalDateTime.setName("party");
        LocalDateTime l = LocalDateTime.now();
        eventWithLocalDateTime.setEventDate(l);

        String json = mapper.writeValueAsString(eventWithLocalDateTime);

        System.out.println(json);
    }


    @Test
    public void dateTimeTestSerRestAssured() {
        EventWithLocalDateTime eventWithLocalDateTime = new EventWithLocalDateTime();

        eventWithLocalDateTime.setName("party");
        LocalDateTime l = LocalDateTime.now();
        eventWithLocalDateTime.setEventDate(l);

        given().log().all().body(eventWithLocalDateTime).get("http://localhost:8080/api/v1/myapp");

    }

}
