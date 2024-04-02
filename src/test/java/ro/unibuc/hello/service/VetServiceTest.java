package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VetServiceTest {

    @Mock
    private VetRepository mockVetRepository;

    @InjectMocks
    private VetService vetService;

    @Test
    void test_createVet() {
        VetEntity vet = new VetEntity("Dr. Smith", "Neurology");
        when(mockVetRepository.save(any(VetEntity.class))).thenReturn(vet);

        VetEntity createdVet = vetService.createVet(vet);

        Assertions.assertNotNull(createdVet);
        Assertions.assertEquals(vet.getName(), createdVet.getName());
        Assertions.assertEquals(vet.getSpecialization(), createdVet.getSpecialization());
    }

    @Test
    void test_getAllVets() {
        List<VetEntity> vets = Arrays.asList(new VetEntity("Dr. Smith", "Neurology"), new VetEntity("Dr. Jones", "Surgery"));
        when(mockVetRepository.findAll()).thenReturn(vets);

        List<VetEntity> result = vetService.getAllVets();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void test_getVetById() {
        VetEntity vet = new VetEntity("Dr. Smith", "Neurology");
        when(mockVetRepository.findById("1")).thenReturn(Optional.of(vet));

        VetEntity foundVet = vetService.getVetById("1");

        Assertions.assertNotNull(foundVet);
        Assertions.assertEquals(vet.getName(), foundVet.getName());
    }

    @Test
    void test_updateVet() {
        VetEntity originalVet = new VetEntity("Dr. Smith", "Neurology");
        VetEntity updatedDetails = new VetEntity("Dr. Smith", "Pediatric Neurology");
        when(mockVetRepository.findById("1")).thenReturn(Optional.of(originalVet));
        when(mockVetRepository.save(any(VetEntity.class))).thenReturn(updatedDetails);

        VetEntity updatedVet = vetService.updateVet("1", updatedDetails);

        Assertions.assertEquals("Pediatric Neurology", updatedVet.getSpecialization());
    }

    @Test
    void test_deleteVet() {
        Assertions.assertDoesNotThrow(() -> vetService.deleteVet("1"));
    }
}
