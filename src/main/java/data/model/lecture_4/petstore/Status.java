package data.model.lecture_4.petstore;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    @JsonValue
    private final String value;

    Status(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}