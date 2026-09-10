package data.model.lecture_4.petstore.lombok_fluent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public class TagsItem {

    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public int id;
}