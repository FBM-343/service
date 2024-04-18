package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.dto.AppointmentDTO;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicLong;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private MeterRegistry metricsRegistry;

    private final AtomicLong counter = new AtomicLong();

    public AppointmentEntity createAppointment(AppointmentEntity appointment) {
        PetEntity pet = petRepository.findById(appointment.getPet().getId())
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + appointment.getPet().getId()));
        VetEntity vet = vetRepository.findById(appointment.getVet().getId())
                .orElseThrow(() -> new RuntimeException("Vet not found with id: " + appointment.getVet().getId()));

        appointment.setPet(pet);
        appointment.setVet(vet);
        return appointmentRepository.save(appointment);
    }

    public List<AppointmentEntity> getAllAppointments() {
        metricsRegistry.counter("getAllAppointments_number", "endpoint", "appointments").increment(counter.incrementAndGet());
        return appointmentRepository.findAll();
    }

    public AppointmentEntity getAppointmentById(String id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public AppointmentEntity updateAppointment(String id, AppointmentDTO appointmentDetails) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        
        if (appointmentDetails.getAppointmentTime() != null) {
            appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        }
        if (appointmentDetails.getReason() != null) {
            appointment.setReason(appointmentDetails.getReason());
        }
        if (appointmentDetails.getPetId() != null) {
            PetEntity pet = petRepository.findById(appointmentDetails.getPetId())
                    .orElseThrow(() -> new RuntimeException("Pet not found with id: " + appointmentDetails.getPetId()));
            appointment.setPet(pet);
        }
        if (appointmentDetails.getVetId() != null) {
            VetEntity vet = vetRepository.findById(appointmentDetails.getVetId())
                    .orElseThrow(() -> new RuntimeException("Vet not found with id: " + appointmentDetails.getVetId()));
            appointment.setVet(vet);
        }
        return appointmentRepository.save(appointment);
    }
    

    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}
