package data.model.lecture_4.petstore.lombok_builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.util.Arrays;

public class TestBuilder {
    @Test
    public void ts1() throws JsonProcessingException {
        Category category = Category.builder()
                .id(1)
                .name("CategoryName")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(category);

        System.out.println(json);

    }

    @Test
    public void ts2() throws JsonProcessingException {

        Category category = Category.builder()
                .id(1)
                .name("CategoryName")
                .build();

        TagsItem asd = TagsItem.builder()
                .id(134)
                .name("asd")
                .build();

        TagsItem b2 = TagsItem.builder()
                .id(678)
                .name("zxds")
                .build();

        PostPetStore postPetStore = PostPetStore.builder()
                .id(1)
                .name("PetName")
                .photoUrls(Arrays.asList("url1", "url2"))
                .category(category)
                .tags(Arrays.asList(asd, b2))
                .status(Status.AVAILABLE)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(postPetStore);

        System.out.println(json);

    }

}
