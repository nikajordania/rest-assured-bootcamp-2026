package lecture_4;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JacksonAnnotationsTest {
    @Test
    void shouldDeserializeUnknownFields() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String json = """
            {
                "carBrand": "BMW",
                "color": "black",
                "year": 2025
            }
            """;

        Car car = mapper.readValue(json, Car.class);

        assertThat(car.getBrand(), is("BMW"));
        assertThat(car.getUnrecognizedFields().get("color"), is("black"));

        assertThat(car.getUnrecognizedFields().get("year"), is(2025));
    }

    @Test
    void shouldSerializeUnknownFields() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Car car = new Car();
        car.setBrand("BMW");

        String json = mapper.writeValueAsString(car);

        JsonNode result = mapper.readTree(json);

        assertThat(result.get("brand").asText(), is("BMW"));
        assertThat(result.get("name").asText(), is("Jack"));
        assertThat(result.get("surname").asText(), is("wolfskin"));
    }

    @Test
    public void testJsonSetter() {
        Car car = new Car();
        car.setBrand("BMW");
        car.allSetter("carBrand", "Toyota");
        System.out.println(car.getBrand());
        System.out.println(car.getUnrecognizedFields());

        String complexJson = """
                {
                    "carBrand": "Mercedes",
                    "color": "Red",
                    "year": "2020",
                    "engineType": "V8",
                    "unrecognizedField1": "value1",
                    "unrecognizedField2": "value2"
                
                }
                """;

        String complexJson2 = """
                {
                    "carBrand": "Mercedes",
                    "color": "Red",
                    "year": "2020",
                    "engineType": "V8",
                    "unrecognizedField1": "value1",
                    "unrecognizedField2": "value2",
                    "subObject": {
                        "nestedField1": "nestedValue1",
                        "nestedField2": "nestedValue2",
                        "unrecognizedField3": "value3"
                    }
                }
                """;

        // Simulate deserialization
        ObjectMapper mapper = new ObjectMapper();

        try {
            Car deserializedCar = mapper.readValue(complexJson2, Car.class);
            System.out.println("Deserialized Car Brand: " + deserializedCar.getBrand());
            System.out.println("Unrecognized Fields: " + deserializedCar.getUnrecognizedFields());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

@Getter
@Setter
class Car {
    @JsonSetter("carBrand")
    private String brand;
    private Map<String, String> unrecognizedFields = new HashMap<>();

    private Map<String, Object> subObject;

    @JsonAnySetter
    public void allSetter(String fieldName, String fieldValue) {
        unrecognizedFields.put(fieldName, fieldValue);
    }

    @JsonAnyGetter
    Map<String, String> map = Map.of(
            "name", "Jack",
            "surname", "wolfskin"
    );
}
