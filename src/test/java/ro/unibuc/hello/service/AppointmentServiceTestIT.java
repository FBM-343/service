package ro.unibuc.hello.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.dto.AppointmentDTO;

@SpringBootTest
@Tag("IT")
public class AppointmentServiceTestIT {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    VetRepository vetRepository;

    @Autowired
    PetRepository petRepository;

    @Test
    public void test_createAppointment() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");
        petRepository.save(pet);

        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");
        vetRepository.save(vet);

        AppointmentEntity appointment = new AppointmentEntity(pet, vet, LocalDateTime.now(), "Vaccin");
        appointment.setId("1");

        AppointmentEntity createdAppointment = appointmentService.createAppointment(appointment);
        Assertions.assertTrue(createdAppointment.getReason().equals("Vaccin"));
    }

    @Test
    public void test_getAppointment() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");
        petRepository.save(pet);

        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");
        vetRepository.save(vet);

        AppointmentEntity appointment = new AppointmentEntity(pet, vet, LocalDateTime.now(), "Vaccin");
        appointment.setId("1");

        appointmentService.createAppointment(appointment);
        AppointmentEntity foundAppointment = appointmentService.getAppointmentById("1");
        Assertions.assertTrue(foundAppointment.getReason().equals("Vaccin"));
    }

    @Test
    public void test_updateAppointment() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");
        petRepository.save(pet);

        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");
        vetRepository.save(vet);

        AppointmentEntity appointment = new AppointmentEntity(pet, vet, LocalDateTime.now(), "Vaccin");
        appointment.setId("1");

        appointmentService.createAppointment(appointment);

        AppointmentDTO updateDetails = new AppointmentDTO();
        updateDetails.setReason("Operatie");

        AppointmentEntity updatedAppointment = appointmentService.updateAppointment("1", updateDetails);
    
        Assertions.assertTrue(updatedAppointment.getReason().equals("Operatie"));
    }

    @Test
    public void test_deleteAppointment() {
        PetEntity pet = new PetEntity("Whiskers", "Pisică");
        pet.setId("1");
        petRepository.save(pet);

        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara");
        vet.setId("1");
        vetRepository.save(vet);

        AppointmentEntity appointment = new AppointmentEntity(pet, vet, LocalDateTime.now(), "Vaccin");
        appointment.setId("1");

        appointmentService.createAppointment(appointment);
        appointmentService.deleteAppointment("1");

        try {
            appointmentService.getAppointmentById("1");
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Appointment not found"));
        }
    }
}