package data.model.lecture_4.reqres.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Data(

	@JsonProperty("last_name")
	String lastName,

	@JsonProperty("id")
	Integer id,

	@JsonProperty("avatar")
	String avatar,

	@JsonProperty("first_name")
	String firstName,

	@JsonProperty("email")
	String email
) {
}