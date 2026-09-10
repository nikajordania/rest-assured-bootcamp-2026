package data.model.lecture_4;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Event {
    private String name;
    private LocalDate date;           // dd-MM-yyyy
    private LocalDateTime dateTime;   // dd-MM-yyyy HH:mm:ss
}
