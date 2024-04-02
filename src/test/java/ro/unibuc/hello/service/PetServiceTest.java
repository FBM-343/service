package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.PetRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository mockPetRepository;

    @InjectMocks
    private PetService petService = new PetService();

    @Test
    void test_createPet() {
        PetEntity pet = new PetEntity("Luna", "Cat");
        when(mockPetRepository.save(any(PetEntity.class))).thenReturn(pet);

        PetEntity createdPet = petService.createPet(pet);

        Assertions.assertNotNull(createdPet);
        Assertions.assertEquals(pet.getId(), createdPet.getId());
        Assertions.assertEquals(pet.getName(), createdPet.getName());
        Assertions.assertEquals(pet.getSpecies(), createdPet.getSpecies());
    }

    @Test
    void test_getAllPets() {
        List<PetEntity> pets = Arrays.asList(new PetEntity("Luna", "Cat"), new PetEntity("Buddy", "Dog"));
        when(mockPetRepository.findAll()).thenReturn(pets);

        List<PetEntity> result = petService.getAllPets();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void test_getPetById() {
        PetEntity pet = new PetEntity("Luna", "Cat");
        when(mockPetRepository.findById("1")).thenReturn(Optional.of(pet));

        PetEntity foundPet = petService.getPetById("1");

        Assertions.assertNotNull(foundPet);
        Assertions.assertEquals(pet.getId(), foundPet.getId());
    }

    @Test
    void test_updatePet() {
        PetEntity originalPet = new PetEntity("Luna", "Cat");
        PetEntity updatedDetails = new PetEntity("Luna", "Dog");
        when(mockPetRepository.findById("1")).thenReturn(Optional.of(originalPet));
        when(mockPetRepository.save(any(PetEntity.class))).thenReturn(updatedDetails);

        PetEntity updatedPet = petService.updatePet("1", updatedDetails);

        Assertions.assertEquals("Dog", updatedPet.getSpecies());
    }

    @Test
    void test_deletePet() {
        Assertions.assertDoesNotThrow(() -> petService.deletePet("1"));
    }
}
