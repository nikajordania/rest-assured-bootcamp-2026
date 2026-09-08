package data.model.reqres.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {

    @JsonProperty("data")
    private Data data;

    @JsonProperty("support")
    private Support support;
}