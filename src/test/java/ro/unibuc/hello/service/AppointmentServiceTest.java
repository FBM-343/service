package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.cucumber.java.bs.A;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.dto.AppointmentDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository mockAppointmentRepository;

    @Mock
    private PetRepository mockPetRepository;

    @Mock
    private VetRepository mockVetRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void test_createAppointment() {
        // Create entities with IDs
        PetEntity pet = new PetEntity("Whiskers1", "Pisică");
        pet.setId("petId123"); // Set a mock ID
        VetEntity vet = new VetEntity("Dr. Smith1", "Medicina veterinara interna");
        vet.setId("vetId123"); // Set a mock ID
        AppointmentEntity appointment = new AppointmentEntity(pet, vet, LocalDateTime.now(), "Checkup");

        // Mock the behavior of findById to return the created entities when their
        // specific IDs are queried
        when(mockPetRepository.findById("petId123")).thenReturn(Optional.of(pet));
        when(mockVetRepository.findById("vetId123")).thenReturn(Optional.of(vet));
        // Mock the save behavior as before
        when(mockAppointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);

        AppointmentEntity createdAppointment = appointmentService.createAppointment(appointment);

        Assertions.assertNotNull(createdAppointment);
        // Since the entities have IDs, these assertions should pass without throwing
        // the exception
        Assertions.assertEquals(pet.getId(), createdAppointment.getPet().getId());
        Assertions.assertEquals(vet.getId(), createdAppointment.getVet().getId());
    }

    @Test
    void test_getAllAppointments() {
        List<AppointmentEntity> appointments = Arrays.asList(
                new AppointmentEntity(new PetEntity("Luna", "Cat"), new VetEntity("Dr. Smith", "Neurology"),
                        LocalDateTime.now(), "Checkup"),
                new AppointmentEntity(new PetEntity("Buddy", "Dog"), new VetEntity("Dr. Jones", "Surgery"),
                        LocalDateTime.now(), "Vaccination"));
        when(mockAppointmentRepository.findAll()).thenReturn(appointments);

        List<AppointmentEntity> result = appointmentService.getAllAppointments();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void test_getAppointmentById() {
        AppointmentEntity appointment = new AppointmentEntity(new PetEntity("Luna", "Cat"),
                new VetEntity("Dr. Smith", "Neurology"), LocalDateTime.now(), "Checkup");
        when(mockAppointmentRepository.findById("1")).thenReturn(Optional.of(appointment));

        AppointmentEntity foundAppointment = appointmentService.getAppointmentById("1");

        Assertions.assertNotNull(foundAppointment);
        Assertions.assertEquals("Checkup", foundAppointment.getReason());
    }

    @Test
    void test_updateAppointment() {
        PetEntity dog = new PetEntity("Rex", "Dog");
        PetEntity cat = new PetEntity("Mex", "Cat");
        dog.setId("1");
        cat.setId("2");

        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara interna");
        VetEntity vet2 = new VetEntity("Dr. John", "Chirurgie veterinara");
        vet.setId("1");
        vet2.setId("2");

        LocalDateTime now = LocalDateTime.now();

        AppointmentEntity originalAppointment = new AppointmentEntity(dog, vet, now, "Vaccin");
        AppointmentEntity updatedAppointment = new AppointmentEntity(cat, vet2, now.plusDays(1), "Operatie");

        AppointmentDTO appointmentDetails = new AppointmentDTO();
        appointmentDetails.setPetId("2");
        appointmentDetails.setVetId("2");
        appointmentDetails.setAppointmentTime(now.plusDays(1));
        appointmentDetails.setReason("Operatie");

        when(mockPetRepository.findById("1")).thenReturn(Optional.of(dog));
        when(mockPetRepository.findById("2")).thenReturn(Optional.of(cat));

        when(mockVetRepository.findById("1")).thenReturn(Optional.of(vet));
        when(mockVetRepository.findById("2")).thenReturn(Optional.of(vet2));

        when(mockAppointmentRepository.findById("1")).thenReturn(Optional.of(originalAppointment));
        when(mockAppointmentRepository.save(any(AppointmentEntity.class))).thenReturn(updatedAppointment);

        AppointmentEntity updatedAppointmentResult = appointmentService.updateAppointment("1", appointmentDetails);
        Assertions.assertEquals(appointmentDetails.getReason(), updatedAppointmentResult.getReason());
        Assertions.assertEquals(appointmentDetails.getAppointmentTime(), updatedAppointmentResult.getAppointmentTime());
        Assertions.assertEquals(appointmentDetails.getPetId(), updatedAppointmentResult.getPet().getId());
        Assertions.assertEquals(appointmentDetails.getVetId(), updatedAppointmentResult.getVet().getId());
    }

    @Test
    void test_deleteAppointment() {
        Assertions.assertDoesNotThrow(() -> appointmentService.deleteAppointment("1"));
    }
}
