package data.model.lecture_4.reqres.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta(

	@JsonProperty("docs_url")
	String docsUrl,

	@JsonProperty("cta")
	Cta cta,

	@JsonProperty("powered_by")
	String poweredBy,

	@JsonProperty("variant")
	String variant,

	@JsonProperty("context")
	String context,

	@JsonProperty("message")
	String message,

	@JsonProperty("example_url")
	String exampleUrl,

	@JsonProperty("upgrade_url")
	String upgradeUrl
) {
}