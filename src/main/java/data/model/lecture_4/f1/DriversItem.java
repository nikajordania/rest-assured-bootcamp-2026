package data.model.lecture_4.f1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriversItem {

    @JsonProperty("code")
    private String code;

    @JsonProperty("driverId")
    private String driverId;

    @JsonProperty("permanentNumber")
    private String permanentNumber;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("givenName")
    private String givenName;

    @JsonProperty("familyName")
    private String familyName;

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;

    @JsonProperty("url")
    private String url;

    public String getCode() {
        return code;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getPermanentNumber() {
        return permanentNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "DriversItem{" +
                        "code = '" + code + '\'' +
                        ",driverId = '" + driverId + '\'' +
                        ",permanentNumber = '" + permanentNumber + '\'' +
                        ",nationality = '" + nationality + '\'' +
                        ",givenName = '" + givenName + '\'' +
                        ",familyName = '" + familyName + '\'' +
                        ",dateOfBirth = '" + dateOfBirth + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}