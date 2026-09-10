package lecture_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.model.lecture_4.DateFormatsExample;
import data.model.lecture_4.timeexample.EventWithLocalDateTime;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class RestAssuredConfigTest {


    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";

        RestAssured.config = config().objectMapperConfig(
                objectMapperConfig()
                        .defaultObjectMapperType(ObjectMapperType.JACKSON_2)
                        .jackson2ObjectMapperFactory(new Jackson2ObjectMapperFactory() {
                            @Override
                            public ObjectMapper create(Type cls, String charset) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                objectMapper.registerModule(new JavaTimeModule());
                                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

//                              default serialization  "eventDate": 1696062134000
//                              disabled mode          "eventDate": "2025-09-30T12:45:32.123"

                                return objectMapper;
                            }
                        })
        );
    }

    @Test
    public void t1() {
        config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig()
                .defaultObjectMapperType(ObjectMapperType.JACKSON_2)
                .jackson2ObjectMapperFactory((cls, charset) -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    return objectMapper;
                })
        );


        ObjectMapper customMapper = new ObjectMapper();
        customMapper.registerModule(new JavaTimeModule());
        customMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        EventWithLocalDateTime eventWithLocalDateTime = new EventWithLocalDateTime();
        eventWithLocalDateTime.setName("Test Event");
        eventWithLocalDateTime.setEventDate(LocalDateTime.now());

        given()
                .contentType("application/json")
                .body(eventWithLocalDateTime)
                .when()
                .post("/events")
                .then()
                .statusCode(201);
    }

    @Test
    public void t2() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        EventWithLocalDateTime eventWithLocalDateTime = new EventWithLocalDateTime();
        eventWithLocalDateTime.setName("Test Event");
        eventWithLocalDateTime.setEventDate(LocalDateTime.now());

        RestAssuredConfig customConfig = config().objectMapperConfig(
                objectMapperConfig()
                        .defaultObjectMapperType(ObjectMapperType.JACKSON_2)
                        .jackson2ObjectMapperFactory((cls, charset) -> objectMapper)
        );

        given()
                .config(customConfig)
                .contentType("application/json")
                .body(eventWithLocalDateTime)
                .when()
                .post("/events")
                .then()
                .statusCode(201);
    }

    @Test
    public void t3() throws JsonProcessingException {
        EventWithLocalDateTime event = new EventWithLocalDateTime();
        event.setName("Conference");
        event.setEventDate(LocalDateTime.of(2025, 6, 14, 15, 30));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writeValueAsString(event);
        System.out.println(json);
    }

    @Test
    public void t4() throws JsonProcessingException {
        DateFormatsExample dto = new DateFormatsExample();
        dto.setIsoDate(LocalDate.of(2025, 6, 14));
        dto.setEuropeanDate(LocalDate.of(2025, 6, 14));
        dto.setIsoDateTime(LocalDateTime.of(2025, 6, 14, 15, 30, 0));
        dto.setCustomDateTime(LocalDateTime.of(2025, 6, 14, 15, 30));
        dto.setVerboseDateTime(LocalDateTime.of(2025, 6, 14, 15, 30, 0));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        System.out.println(json);
    }

    @Test
    public void t5() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String inputJson = """
                {
                  "isoDate": "2025-06-14",
                  "europeanDate": "14/06/2025",
                  "isoDateTime": "2025-06-14T15:30:00",
                  "customDateTime": "14-06-2025 15:30",
                  "verboseDateTime": "Saturday, 14 June 2025 15:30:00"
                }
                """;

        DateFormatsExample dto = mapper.readValue(inputJson, DateFormatsExample.class);
        System.out.println(dto);

    }
}

