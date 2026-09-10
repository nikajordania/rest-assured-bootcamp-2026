package data.model.lecture_4.f1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DriverTable {

    @JsonProperty("Drivers")
    private List<DriversItem> drivers;

    @JsonProperty("season")
    private String season;

    public List<DriversItem> getDrivers() {
        return drivers;
    }

    public String getSeason() {
        return season;
    }

    @Override
    public String toString() {
        return
                "DriverTable{" +
                        "drivers = '" + drivers + '\'' +
                        ",season = '" + season + '\'' +
                        "}";
    }
}