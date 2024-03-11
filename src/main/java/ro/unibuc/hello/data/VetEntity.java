package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VetEntity {

    @Id
    private String id;
    private String name;
    private String specialization;

    public VetEntity() {}

    public VetEntity(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // toString
    @Override
    public String toString() {
        return "VetEntity{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", specialization='" + specialization + '\'' +
               '}';
    }
}
