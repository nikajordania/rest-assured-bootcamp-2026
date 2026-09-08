package data.model.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingResponseItem {

    @JsonProperty("bookingid")
    private Integer bookingid;

    public Integer getBookingid() {
        return bookingid;
    }
}