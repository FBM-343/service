package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.service.AppointmentService;

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

    @GetMapping
    public List<AppointmentEntity> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentEntity getAppointmentById(@PathVariable String id) {
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}")
    public AppointmentEntity updateAppointment(@PathVariable String id, @RequestBody AppointmentEntity appointmentDetails) {
        return appointmentService.updateAppointment(id, appointmentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id) {
        appointmentService.deleteAppointment(id);
    }
}
