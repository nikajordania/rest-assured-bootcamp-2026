package data.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MRData {

    @JsonProperty("xmlns")
    private String xmlns;

    @JsonProperty("total")
    private String total;

    @JsonProperty("offset")
    private String offset;

    @JsonProperty("series")
    private String series;

    @JsonProperty("limit")
    private String limit;

    @JsonProperty("DriverTable")
    private DriverTable driverTable;

    @JsonProperty("url")
    private String url;

    public String getXmlns() {
        return xmlns;
    }

    public String getTotal() {
        return total;
    }

    public String getOffset() {
        return offset;
    }

    public String getSeries() {
        return series;
    }

    public String getLimit() {
        return limit;
    }

    public DriverTable getDriverTable() {
        return driverTable;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "MRData{" +
                        "xmlns = '" + xmlns + '\'' +
                        ",total = '" + total + '\'' +
                        ",offset = '" + offset + '\'' +
                        ",series = '" + series + '\'' +
                        ",limit = '" + limit + '\'' +
                        ",driverTable = '" + driverTable + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}