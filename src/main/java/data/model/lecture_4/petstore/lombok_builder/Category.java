package data.model.lecture_4.petstore.lombok_builder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class Category {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;
}
