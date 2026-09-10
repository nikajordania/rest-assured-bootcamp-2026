package data.model.lecture_4.reqres.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(

	@JsonProperty("data")
	Data data,

	@JsonProperty("_meta")
	Meta meta,

	@JsonProperty("support")
	Support support
) {
}