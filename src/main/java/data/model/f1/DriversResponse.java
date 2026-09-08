package data.model.f1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriversResponse {

    @JsonProperty("MRData")
    private MRData mRData;

    public MRData getMRData() {
        return mRData;
    }

    @Override
    public String toString() {
        return
                "DriversResponse{" +
                        "mRData = '" + mRData + '\'' +
                        "}";
    }
}