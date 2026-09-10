package data.model.lecture_4.reqres.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Support(

	@JsonProperty("text")
	String text,

	@JsonProperty("url")
	String url
) {
}