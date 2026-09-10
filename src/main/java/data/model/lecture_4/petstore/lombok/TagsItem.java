package data.model.lecture_4.petstore.lombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagsItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;
}