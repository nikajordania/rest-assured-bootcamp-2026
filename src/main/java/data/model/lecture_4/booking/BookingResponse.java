package data.model.lecture_4.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class BookingResponse {

    @JsonProperty("BookingResponse")
    private List<BookingResponseItem> bookingResponse;
}
