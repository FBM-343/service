package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.PetRepository;

@SpringBootTest
@Tag("IT")
public class PetServiceTestIT {

    @Autowired
    PetRepository petRepository;

    @Autowired
    PetService petService;

    @Test
    public void test_createPet() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");

        PetEntity createdPet = petService.createPet(pet);
        Assertions.assertTrue(createdPet.getName().equals("Whiskers"));
    }

    @Test
    public void test_getPet() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");

        petService.createPet(pet);
        PetEntity foundPet = petService.getPetById("1");
        Assertions.assertTrue(foundPet.getName().equals("Whiskers"));
    }

    @Test
    public void test_updatePet() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");

        petService.createPet(pet);
        PetEntity updatedPet = petService.updatePet("1", new PetEntity("Whiskers1", "Pisică"));
        Assertions.assertTrue(updatedPet.getName().equals("Whiskers1"));
    }

    @Test
    public void test_deletePet() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");

        petService.createPet(pet);
        petService.deletePet("1");
        try {
            petService.getPetById("1");
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Pet not found"));
        }
    }
}
