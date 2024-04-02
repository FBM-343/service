package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;

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
    
        // Mock the behavior of findById to return the created entities when their specific IDs are queried
        when(mockPetRepository.findById("petId123")).thenReturn(Optional.of(pet));
        when(mockVetRepository.findById("vetId123")).thenReturn(Optional.of(vet));
        // Mock the save behavior as before
        when(mockAppointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointment);
    
        AppointmentEntity createdAppointment = appointmentService.createAppointment(appointment);
    
        Assertions.assertNotNull(createdAppointment);
        // Since the entities have IDs, these assertions should pass without throwing the exception
        Assertions.assertEquals(pet.getId(), createdAppointment.getPet().getId());
        Assertions.assertEquals(vet.getId(), createdAppointment.getVet().getId());
    }
    

    @Test
    void test_getAllAppointments() {
        List<AppointmentEntity> appointments = Arrays.asList(
                new AppointmentEntity(new PetEntity("Luna", "Cat"), new VetEntity("Dr. Smith", "Neurology"), LocalDateTime.now(), "Checkup"),
                new AppointmentEntity(new PetEntity("Buddy", "Dog"), new VetEntity("Dr. Jones", "Surgery"), LocalDateTime.now(), "Vaccination")
        );
        when(mockAppointmentRepository.findAll()).thenReturn(appointments);

        List<AppointmentEntity> result = appointmentService.getAllAppointments();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void test_getAppointmentById() {
        AppointmentEntity appointment = new AppointmentEntity(new PetEntity("Luna", "Cat"), new VetEntity("Dr. Smith", "Neurology"), LocalDateTime.now(), "Checkup");
        when(mockAppointmentRepository.findById("1")).thenReturn(Optional.of(appointment));

        AppointmentEntity foundAppointment = appointmentService.getAppointmentById("1");

        Assertions.assertNotNull(foundAppointment);
        Assertions.assertEquals("Checkup", foundAppointment.getReason());
    }


    @Test
    void test_deleteAppointment() {
        Assertions.assertDoesNotThrow(() -> appointmentService.deleteAppointment("1"));
    }
}
