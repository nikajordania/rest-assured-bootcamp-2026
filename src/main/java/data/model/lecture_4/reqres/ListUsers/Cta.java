package data.model.lecture_4.reqres.ListUsers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cta{

	@JsonProperty("label")
	private String label;

	@JsonProperty("url")
	private String url;

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"Cta{" + 
			"label = '" + label + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}