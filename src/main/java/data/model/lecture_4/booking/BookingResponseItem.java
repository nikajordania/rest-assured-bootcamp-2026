package data.model.lecture_4.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class BookingResponseItem {

    @JsonProperty("bookingid")
    private Integer bookingid;
}
