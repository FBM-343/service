package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.dto.AppointmentDTO;
import ro.unibuc.hello.service.AppointmentService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public AppointmentEntity createAppointment(@RequestBody AppointmentEntity appointment) {
        return appointmentService.createAppointment(appointment);
    }
    @Timed(value = "appointments.time", description = "Time taken to return all appointments")
    @Counted(value = "appointments.count", description = "Times all appointments were returned")
    @GetMapping
    public List<AppointmentEntity> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentEntity getAppointmentById(@PathVariable String id) {
        return appointmentService.getAppointmentById(id);
    }

    @PatchMapping("/{id}")
    public AppointmentEntity updateAppointment(@PathVariable String id, @RequestBody AppointmentDTO appointmentDetails) {
        return appointmentService.updateAppointment(id, appointmentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id) {
        appointmentService.deleteAppointment(id);
    }
}