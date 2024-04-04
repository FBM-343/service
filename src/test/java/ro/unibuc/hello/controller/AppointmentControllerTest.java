package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.service.AppointmentService;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.service.PetService;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.service.VetService;
import ro.unibuc.hello.dto.AppointmentDTO;

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
        private AppointmentService appointmentService;

        @InjectMocks
        private AppointmentController appointmentController;

        private MockMvc mockMvc;
        private ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
                objectMapper.registerModule(new JavaTimeModule());
        }

        @Test
        public void testCreateAppointment() throws Exception {
                LocalDateTime now = LocalDateTime.now();
                AppointmentEntity newAppointment = new AppointmentEntity(new PetEntity("Rex", "Dog"),
                                new VetEntity("Dr. Smith", "Medicina veterinara interna"), now,
                                "Vaccinare");
                AppointmentEntity savedAppointment = new AppointmentEntity(new PetEntity("Mex", "Cat"),
                                new VetEntity("Dr. John", "Chirurgie veterinara"), now, "Operatie");
                savedAppointment.setId("1");

                when(appointmentService.createAppointment(any(AppointmentEntity.class))).thenReturn(savedAppointment);
                
                mockMvc.perform(post("/appointments")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newAppointment)))
                                .andExpect(status().isOk())
                               
                                .andExpect(jsonPath("$.reason").value(savedAppointment.getReason()));
        }

        @Test
        public void testGetAllAppointments() throws Exception {
                AppointmentEntity appointment1 = new AppointmentEntity(new PetEntity("Rex", "Dog"),
                                new VetEntity("Dr. Smith", "Medicina veterinara interna"), LocalDateTime.now(),
                                "Vaccinare");
                appointment1.setId("1");
                AppointmentEntity appointment2 = new AppointmentEntity(new PetEntity("Mex", "Cat"),
                                new VetEntity("Dr. John", "Chirurgie veterinara"), LocalDateTime.now(), "Operatie");
                appointment2.setId("2");

                when(appointmentService.getAllAppointments()).thenReturn(Arrays.asList(appointment1, appointment2));

                mockMvc.perform(get("/appointments")
                                .contentType("application/json"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        public void testGetAppointmentById() throws Exception {
                AppointmentEntity appointment = new AppointmentEntity(new PetEntity("Rex", "Dog"),
                                new VetEntity("Dr. Smith", "Medicina veterinara interna"), LocalDateTime.now(),
                                "Vaccinare");
                appointment.setId("1");

                when(appointmentService.getAppointmentById("1")).thenReturn(appointment);

                mockMvc.perform(get("/appointments/1")
                                .contentType("application/json"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reason").value(appointment.getReason()));
        }

        @Test
        public void testUpdateAppointment() throws Exception {
                PetEntity dog = new PetEntity("Rex", "Dog");
                dog.setId("1");
                VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara interna");
                vet.setId("1");

                PetEntity cat = new PetEntity("Mex", "Cat");
                cat.setId("2");
                VetEntity vet2 = new VetEntity("Dr. John", "Chirurgie veterinara");
                vet2.setId("2");

                AppointmentEntity appointment = new AppointmentEntity(dog,
                                vet, LocalDateTime.now(),
                                "Vaccinare");
                appointment.setId("1");

                AppointmentDTO newAppointment = new AppointmentDTO();
                newAppointment.setPetId("2");
                newAppointment.setVetId("2");
                newAppointment.setAppointmentTime(LocalDateTime.now().plusDays(1));
                newAppointment.setReason("Operatie");

                AppointmentEntity updatedAppointment = new AppointmentEntity(cat,
                                vet2, LocalDateTime.now().plusDays(1),
                                "Operatie");

                updatedAppointment.setId("1");

                when(appointmentService.updateAppointment(eq("1"), any(AppointmentDTO.class))).thenReturn(updatedAppointment);

                mockMvc.perform(patch("/appointments/1")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(newAppointment)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reason").value(updatedAppointment.getReason()));
        }

        @Test
        public void testDeleteAppointment() throws Exception {
                doNothing().when(appointmentService).deleteAppointment("1");

                mockMvc.perform(delete("/appointments/1")
                                .contentType("application/json"))
                                .andExpect(status().isOk());

                verify(appointmentService, times(1)).deleteAppointment("1");
        }
}