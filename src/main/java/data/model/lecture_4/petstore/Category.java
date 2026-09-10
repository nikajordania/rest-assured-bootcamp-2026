package data.model.lecture_4.petstore;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "Category{" +
                        "name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}