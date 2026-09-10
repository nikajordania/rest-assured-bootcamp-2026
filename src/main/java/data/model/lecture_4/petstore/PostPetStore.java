package data.model.lecture_4.petstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "category", "name", "photoUrls", "tags", "status"})
public class PostPetStore {

    @JsonProperty("photoUrls")
    private List<String> photoUrls;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("tags")
    private List<TagsItem> tags;

    @JsonProperty("status")
//    private String status;
    private Status status;

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }

    public List<TagsItem> getTags() {
        return tags;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "PostPetStore{" +
                        "photoUrls = '" + photoUrls + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",category = '" + category + '\'' +
                        ",tags = '" + tags + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}