package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;

import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.dto.AppointmentDTO;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VetRepository vetRepository;

    @PostMapping
    public AppointmentEntity createAppointment(@RequestBody AppointmentEntity appointment) {
        PetEntity pet = petRepository.findById(appointment.getPet().getId())
            .orElseThrow(() -> new RuntimeException("Pet not found with id: " + appointment.getPet().getId()));
        VetEntity vet = vetRepository.findById(appointment.getVet().getId())
            .orElseThrow(() -> new RuntimeException("Vet not found with id: " + appointment.getVet().getId()));

        appointment.setPet(pet);
        appointment.setVet(vet);
        return appointmentRepository.save(appointment);
    }

    @GetMapping
    public List<AppointmentEntity> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentEntity getAppointmentById(@PathVariable String id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    @PatchMapping("/{id}")
    public AppointmentEntity updateAppointment(@PathVariable String id, @RequestBody AppointmentDTO appointmentDetails) {
        AppointmentEntity existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        if (appointmentDetails.getAppointmentTime() != null) {
            existingAppointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        }
        if (appointmentDetails.getReason() != null) {
            existingAppointment.setReason(appointmentDetails.getReason());
        }
        if (appointmentDetails.getPetId() != null) {
            PetEntity pet = petRepository.findById(appointmentDetails.getPetId())
                    .orElseThrow(() -> new RuntimeException("Pet not found with id: " + appointmentDetails.getPetId()));
            existingAppointment.setPet(pet);
        }

        if(appointmentDetails.getVetId() != null) {
            VetEntity vet = vetRepository.findById(appointmentDetails.getVetId())
                    .orElseThrow(() -> new RuntimeException("Vet not found with id: " + appointmentDetails.getVetId()));
            existingAppointment.setVet(vet);
        }

        // Salvare în baza de date
        return appointmentRepository.save(existingAppointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id) {
        appointmentRepository.deleteById(id);
    }
}