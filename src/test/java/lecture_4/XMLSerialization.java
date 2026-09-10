package lecture_4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import data.model.lecture_4.xml.petstore.Category;
import data.model.lecture_4.xml.petstore.Pet;
import data.model.lecture_4.xml.petstore.Tag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.testng.annotations.Test;

public class XMLSerialization {
    @Test
    public void callXMLSerialization() {
        String xmlBody = """
                <?xml version="1.0" encoding="UTF-8"?>
                <Pet>
                    <id>0</id>
                    <Category>
                        <id>0</id>
                        <name>string</name>
                    </Category>
                    <name>doggie</name>
                    <photoUrls>
                        <photoUrl>string</photoUrl>
                    </photoUrls>
                    <tags>
                        <Tag>
                            <id>0</id>
                            <name>string</name>
                        </Tag>
                    </tags>
                    <status>available</status>
                </Pet>""";

        RestAssured.given()
                .log().all()
                .contentType(ContentType.XML)
                .body(xmlBody)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public static void t2() {
        Pet pet = new Pet();
        pet.setId(0);
        pet.setName("doggie");
        pet.setStatus("available");

        Category category = new Category();
        category.setId(0);
        category.setName("string");
        pet.setCategory(category);

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("string");
        pet.setTags(new Tag[]{tag});

        String xmlBody = RestAssured
                .given()
                .log().all()
                .accept(ContentType.XML)
                .contentType(ContentType.XML)
                .body(pet, ObjectMapperType.JAKARTA_EE)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .extract().body().asString();
    }


    @Test
    public static void t3() throws JsonProcessingException {

        XmlMapper xmlMapper = new XmlMapper();

        Pet pet = new Pet();
        pet.setId(0);
        pet.setName("doggie");
        pet.setStatus("available");

        Category category = new Category();
        category.setId(0);
        category.setName("string");
        pet.setCategory(category);

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("string");
        pet.setTags(new Tag[]{tag});

        String xmlBody = xmlMapper.writeValueAsString(pet);
        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(pet, ObjectMapperType.JAKARTA_EE)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all();
    }
}
