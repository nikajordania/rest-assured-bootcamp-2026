package data.model.lecture_4;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EventWithOffsetDateTime {
    private String name;
    private OffsetDateTime eventDateTime;
}