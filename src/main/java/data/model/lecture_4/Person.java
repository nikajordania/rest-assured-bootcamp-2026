package data.model.lecture_4;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class Person {
    private String name;
    private int age;
}
