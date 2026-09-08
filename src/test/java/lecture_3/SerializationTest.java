package lecture_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.model.Fruit;
import data.model.petstore.Category;
import data.model.petstore.PostPetStore;
import data.model.petstore.TagsItem;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class SerializationTest {
    @Test
    void serializeFruitObjectToJson() throws IOException {
        Fruit fruit = Fruit.builder()
                .id(101)
                .name("Apple")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fruit);

        assertEquals("{\"name\":\"Apple\",\"id\":101}", json);
    }

    @Test
    public void serializePetStoreRequest() {
        PostPetStore petRequest = new PostPetStore();
        petRequest.setCategory(new Category(1001, "Fruits"));
        petRequest.setPhotoUrls(List.of("https://example.com/api"));

        TagsItem tag = new TagsItem();
        tag.setName("Tag1");
        petRequest.setTags(List.of(tag));

        given().log().all().body(petRequest).contentType(ContentType.JSON)
                .when().post("https://petstore.swagger.io/v2/pet").then().log().all();
    }
}