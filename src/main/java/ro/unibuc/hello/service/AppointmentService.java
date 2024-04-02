package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetRepository;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VetRepository vetRepository;

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
        return appointmentRepository.findAll();
    }

    public AppointmentEntity getAppointmentById(String id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public AppointmentEntity updateAppointment(String id, AppointmentEntity appointmentDetails) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    
        // Here, you should update the appointment entity with new details.
        // For example:
        appointment.setPet(appointmentDetails.getPet());
        appointment.setVet(appointmentDetails.getVet());
        appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        appointment.setReason(appointmentDetails.getReason());
    
        return appointmentRepository.save(appointment);
    }
    

    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
}
