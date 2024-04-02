package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.service.AppointmentService;
import ro.unibuc.hello.service.PetService;
import ro.unibuc.hello.service.VetService;

import java.util.Arrays;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class AppointmentControllerTest {
        private PetEntity dog = new PetEntity("Rex", "Dog");
        private PetEntity cat = new PetEntity("Mex", "Cat");

        private VetEntity vet1 = new VetEntity("Dr. Smith", "Medicina veterinara interna");
        private VetEntity vet2 = new VetEntity("Dr. John", "Chirurgie veterinara");

        @Mock
        private AppointmentService appointmentRepository;

        @Mock
        private PetService petRepository;

        @Mock
        private VetService vetRepository;

        @InjectMocks
        private AppointmentController appointmentController;

        private MockMvc mockMvc;
        private ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
                objectMapper.registerModule(new JavaTimeModule());

                petRepository.createPet(dog);
                petRepository.createPet(cat);
                vetRepository.createVet(vet1);
                vetRepository.createVet(vet2);

                dog.setId("1");
                cat.setId("2");
                vet1.setId("1");
                vet2.setId("2");
        }

        @Test
        public void testCreateAppointment() throws Exception {
            AppointmentEntity newAppointment = new AppointmentEntity(dog, vet1, LocalDateTime.now(), "Vaccinare");
            AppointmentEntity savedAppointment = new AppointmentEntity(cat, vet2, LocalDateTime.now(), "Operatie");
            savedAppointment.setId("1");
        
            when(appointmentRepository.createAppointment(any(AppointmentEntity.class))).thenReturn(savedAppointment);
        
            mockMvc.perform(post("/appointments")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(newAppointment)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.appointmentTime").value(savedAppointment.getAppointmentTime().toString())) // Convertim timpul la șir de caractere
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

                when(appointmentRepository.getAllAppointments()).thenReturn(Arrays.asList(appointment1, appointment2));

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

                when(appointmentRepository.getAppointmentById("1")).thenReturn(appointment);

                mockMvc.perform(get("/appointments/1")
                                .contentType("application/json"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reason").value(appointment.getReason()));
        }


        @Test
        public void testDeleteAppointment() throws Exception {
                doNothing().when(appointmentRepository).deleteAppointment("1");

                mockMvc.perform(delete("/appointments/1")
                                .contentType("application/json"))
                                .andExpect(status().isOk());

                verify(appointmentRepository, times(1)).deleteAppointment("1");
        }
}