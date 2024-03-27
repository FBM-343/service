package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.VetEntity;

import java.util.Arrays;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class AppointmentControllerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentController appointmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    /*
    @Test
    public void testCreateAppointment() throws Exception {
        AppointmentEntity newAppointment = new AppointmentEntity(new PetEntity("Rex", "Dog"), new VetEntity("Dr. Smith", "Medicina veterinara interna"), LocalDateTime.now(), "Vaccinare");
        AppointmentEntity savedAppointment = new AppointmentEntity(new PetEntity("Mex", "Cat"), new VetEntity("Dr. John", "Chirurgie veterinara"), LocalDateTime.now(), "Operatie");
        savedAppointment.setId("1");

        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(savedAppointment);

        mockMvc.perform(post("/appointments")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newAppointment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentTime").value(savedAppointment.getAppointmentTime()))
                .andExpect(jsonPath("$.reason").value(savedAppointment.getReason()));
    }
    */

    @Test
    public void testGetAllAppointments() throws Exception {
        AppointmentEntity appointment1 = new AppointmentEntity(new PetEntity("Rex", "Dog"), new VetEntity("Dr. Smith", "Medicina veterinara interna"), LocalDateTime.now(), "Vaccinare");
        appointment1.setId("1");
        AppointmentEntity appointment2 = new AppointmentEntity(new PetEntity("Mex", "Cat"), new VetEntity("Dr. John", "Chirurgie veterinara"), LocalDateTime.now(), "Operatie");
        appointment2.setId("2");

        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment1, appointment2));

        mockMvc.perform(get("/appointments")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetAppointmentById() throws Exception {
        AppointmentEntity appointment = new AppointmentEntity(new PetEntity("Rex", "Dog"), new VetEntity("Dr. Smith", "Medicina veterinara interna"), LocalDateTime.now(), "Vaccinare");
        appointment.setId("1");

        when(appointmentRepository.findById("1")).thenReturn(Optional.of(appointment));

        mockMvc.perform(get("/appointments/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value(appointment.getReason()));
    }

    // @Test
    //public void testUpdateAppointment() throws Exception {
    //    AppointmentEntity originalAppointment = new AppointmentEntity(new PetEntity("Rex", "Dog"), new VetEntity("Dr. Smith", "Medicina veterinara interna"), LocalDateTime.now(), "Vaccinare");
    //    originalAppointment.setId("1");
//
    //    AppointmentEntity updatedAppointment = new AppointmentEntity(new PetEntity("Mex", "Cat"), new VetEntity("Dr. John", "Chirurgie veterinara"), LocalDateTime.now(), "Operatie");
    //    updatedAppointment.setId("1");
//
    //    when(appointmentRepository.findById("1")).thenReturn(Optional.of(originalAppointment));
    //    when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(updatedAppointment);
//
    //    mockMvc.perform(put("/appointments/1")
    //            .contentType("application/json")
    //            .content(objectMapper.writeValueAsString(updatedAppointment)))
    //            .andExpect(status().isOk())
    //            .andExpect(jsonPath("$.species").value("Cat"));
    //}

    @Test
    public void testDeleteAppointment() throws Exception {
        doNothing().when(appointmentRepository).deleteById("1");

        mockMvc.perform(delete("/appointments/1")
                .contentType("application/json"))
                .andExpect(status().isOk());

        verify(appointmentRepository, times(1)).deleteById("1");
    }
}