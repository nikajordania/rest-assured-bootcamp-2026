package lecture_4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObjectMapperConfigExample {
    public static ObjectMapper createCustomObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Disable serialization as timestamps (arrays)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Create JavaTimeModule
        JavaTimeModule module = new JavaTimeModule();

        // Define your custom formatters
        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Register serializers and deserializers with custom formatters
        module.addSerializer(LocalDate.class, new LocalDateSerializer(localDateFormatter));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(localDateFormatter));

        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormatter));

        // Register the module
        mapper.registerModule(module);

        return mapper;
    }
}
