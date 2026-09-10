package lecture_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.model.lecture_4.petstore.lombok.Category;
import data.model.lecture_4.petstore.lombok.PostPetStore;
import data.model.lecture_4.petstore.lombok.Status;
import data.model.lecture_4.petstore.lombok.TagsItem;
import org.testng.annotations.Test;

import java.util.List;

public class TestSerialization {
    @Test
    void shouldSerializePostPetStore() throws JsonProcessingException {
        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");

        TagsItem tag1 = new TagsItem();
        tag1.setId(10);
        tag1.setName("friendly");

        TagsItem tag2 = new TagsItem();
        tag2.setId(20);
        tag2.setName("trained");

        PostPetStore pet = new PostPetStore();

        pet.setId(100);
        pet.setName("Buddy");
        pet.setCategory(category);
        pet.setPhotoUrls(List.of(
                "https://example.com/dog.jpg",
                "https://example.com/dog2.jpg"
        ));
        pet.setTags(List.of(tag1, tag2));
        pet.setStatus(Status.AVAILABLE);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(pet);

        System.out.println(json);
    }
}
