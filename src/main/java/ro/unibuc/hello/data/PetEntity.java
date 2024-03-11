package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PetEntity {

    @Id
    private String id;
    private String name;
    private String species;

    public PetEntity() {}

    public PetEntity(String name, String species) {
        this.name = name;
        this.species = species;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    // toString
    @Override
    public String toString() {
        return "PetEntity{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", species='" + species + '\'' +
               '}';
    }
}
