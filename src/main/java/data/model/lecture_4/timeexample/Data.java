package data.model.lecture_4.timeexample;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data{

	@JsonProperty("Capacity")
	private String capacity;

	@JsonProperty("Screen size")
	private Double screenSize;

	public void setCapacity(String capacity){
		this.capacity = capacity;
	}

	public String getCapacity(){
		return capacity;
	}

	public void setScreenSize(Double screenSize){
		this.screenSize = screenSize;
	}

	public Double getScreenSize(){
		return screenSize;
	}
}