package data.model.lecture_4.petstore.lombok_builder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
@JsonPropertyOrder({"id", "category", "name", "photoUrls", "tags", "status"})
public class PostPetStore {

    @JsonProperty("photoUrls")
    private List<String> photoUrls;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("tags")
    private List<TagsItem> tags;

    @JsonProperty("status")
    private Status status;
}