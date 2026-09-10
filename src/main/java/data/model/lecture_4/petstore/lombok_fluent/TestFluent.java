package data.model.lecture_4.petstore.lombok_fluent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.testng.annotations.Test;

import java.util.Arrays;

public class TestFluent {
    @Test
    public void ts1() throws JsonProcessingException {
        Category category = new Category()
                .id(1)
                .name("CategoryName");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(category);

        System.out.println(json);

    }

    @Test
    public void ts2() throws JsonProcessingException {

        Category category = new Category()
                .id(1)
                .name("CategoryName");

        TagsItem asd = new TagsItem()
                .id(134)
                .name("asd");

        TagsItem b2 = new TagsItem()
                .id(678)
                .name("zxds");

        PostPetStore postPetStore = new PostPetStore()
                .id(1)
                .name("PetName")
                .photoUrls(Arrays.asList("url1", "url2"))
                .category(category)
                .tags(Arrays.asList(asd, b2))
                .status(Status.AVAILABLE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(postPetStore);

        System.out.println(json);

    }

}
