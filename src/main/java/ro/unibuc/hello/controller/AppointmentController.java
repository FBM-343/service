package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;

import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;

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

    @PutMapping("/{id}")
    public AppointmentEntity updateAppointment(@PathVariable String id, @RequestBody AppointmentEntity appointmentDetails) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        // Pot fi adăugate actualizări suplimentare aici
        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id) {
        appointmentRepository.deleteById(id);
    }
}
