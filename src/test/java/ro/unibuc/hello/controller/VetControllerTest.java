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
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.service.VetService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class VetControllerTest {

    @Mock
    private VetService vetRepository;

    @InjectMocks
    private VetController vetController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    public void testCreateVet() throws Exception {
        VetEntity newVet = new VetEntity("Dr. Smith", "Medicina veterinara interna");
        VetEntity savedVet = new VetEntity("Dr. Jones", "Chirurgie veterinara");
        savedVet.setId("1");

        when(vetRepository.createVet(any(VetEntity.class))).thenReturn(savedVet);

        mockMvc.perform(post("/vets")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newVet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(savedVet.getName()))
                .andExpect(jsonPath("$.specialization").value(savedVet.getSpecialization()));
    }

    @Test
    public void testGetAllVets() throws Exception {
        VetEntity vet1 = new VetEntity("Dr. Smith", "Medicina veterinara interna");
        vet1.setId("1");
        VetEntity vet2 = new VetEntity("Dr. Jones", "Chirurgie veterinara");
        vet2.setId("2");

        when(vetRepository.getAllVets()).thenReturn(Arrays.asList(vet1, vet2));

        mockMvc.perform(get("/vets")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetVetById() throws Exception {
        VetEntity vet = new VetEntity("Dr. Smith", "Medicina veterinara interna");
        vet.setId("1");

        when(vetRepository.getVetById("1")).thenReturn(vet);

        mockMvc.perform(get("/vets/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(vet.getName()));
    }

@Test
public void testUpdateVet() throws Exception {
    VetEntity originalVet = new VetEntity("Dr. Jones", "Chirurgie veterinara");
    originalVet.setId("1");

    VetEntity updatedVet = new VetEntity("Dr. Snow", "Neurology");
    updatedVet.setId("1");

    when(vetRepository.updateVet(eq("1"), any(VetEntity.class))).thenReturn(updatedVet); // Modificare aici

    mockMvc.perform(put("/vets/1")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedVet)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.specialization").value("Neurology"));
}


    @Test
    public void testDeleteVet() throws Exception {
        doNothing().when(vetRepository).deleteVet("1");

        mockMvc.perform(delete("/vets/1")
                .contentType("application/json"))
                .andExpect(status().isOk());

        verify(vetRepository, times(1)).deleteVet("1");
    }
}
