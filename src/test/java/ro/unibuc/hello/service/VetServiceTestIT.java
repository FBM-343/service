package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;

@SpringBootTest
@Tag("IT")
public class VetServiceTestIT {

    @Autowired
    VetRepository vetRepository;

    @Autowired
    VetService vetService;

    @Test
    public void test_createVet() {
        VetEntity vet = new VetEntity("Whiskers", "Pisică");
        vet.setId("1");

        VetEntity createdVet = vetService.createVet(vet);
        Assertions.assertTrue(createdVet.getName().equals("Whiskers"));
    }

    @Test
    public void test_getVet() {
        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");

        vetService.createVet(vet);
        VetEntity foundVet = vetService.getVetById("1");
        Assertions.assertTrue(foundVet.getName().equals("Dr. Smith"));
    }

    @Test
    public void test_updateVet() {
        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");

        vetService.createVet(vet);
        VetEntity updatedVet = vetService.updateVet("1", new VetEntity("Dr. John", "Medicina veterinara"));
        Assertions.assertTrue(updatedVet.getName().equals("Dr. John"));
    }

    @Test
    public void test_deleteVet() {
        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");

        vetService.createVet(vet);
        vetService.deleteVet("1");
        try {
            vetService.getVetById("1");
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Vet not found"));
        }
    }
}