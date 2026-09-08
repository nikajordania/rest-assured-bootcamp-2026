package data.model.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookingResponse {

    @JsonProperty("BookingResponse")
    private List<BookingResponseItem> bookingResponse;

    public List<BookingResponseItem> getBookingResponse() {
        return bookingResponse;
    }
}