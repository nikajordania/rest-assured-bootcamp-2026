package data.model.lecture_4;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DateFormatsExample {

    // ISO_LOCAL_DATE: 2025-06-14
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate isoDate;

    // CUSTOM DATE: 14/06/2025
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate europeanDate;

    // ISO_LOCAL_DATE_TIME: 2025-06-14T15:30:00
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime isoDateTime;

    // CUSTOM DATETIME: 14-06-2025 15:30
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime customDateTime;

    // FULL DATETIME: Saturday, 14 June 2025 15:30:00
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, dd MMMM yyyy HH:mm:ss")
    private LocalDateTime verboseDateTime;
}
