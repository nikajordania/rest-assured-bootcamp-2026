package data.model.lecture_4.reqres.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Cta(

	@JsonProperty("label")
	String label,

	@JsonProperty("url")
	String url
) {
}