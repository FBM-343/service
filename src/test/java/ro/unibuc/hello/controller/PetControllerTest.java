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
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.service.PetService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class PetControllerTest {

    @Mock
    private PetService petRepository;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    public void testCreatePet() throws Exception {
        PetEntity newPet = new PetEntity("Rex", "Dog");
        PetEntity savedPet = new PetEntity("Mex", "Cat");
        savedPet.setId("1");

        when(petRepository.createPet(any(PetEntity.class))).thenReturn(savedPet);

        mockMvc.perform(post("/pets")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newPet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(savedPet.getName()))
                .andExpect(jsonPath("$.species").value(savedPet.getSpecies()));
    }

    @Test
    public void testGetAllPets() throws Exception {
        PetEntity pet1 = new PetEntity("Rex", "Dog");
        pet1.setId("1");
        PetEntity pet2 = new PetEntity("Mex", "Cat");
        pet2.setId("2");

        when(petRepository.getAllPets()).thenReturn(Arrays.asList(pet1, pet2));

        mockMvc.perform(get("/pets")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetPetById() throws Exception {
        PetEntity pet = new PetEntity("Rex", "Dog");
        pet.setId("1");

        when(petRepository.getPetById("1")).thenReturn(pet);

        mockMvc.perform(get("/pets/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(pet.getName()));
    }

@Test
public void testUpdatePet() throws Exception {
    PetEntity originalPet = new PetEntity("Rex", "Dog");
    originalPet.setId("1");

    PetEntity updatedPet = new PetEntity("Mex", "Cat");
    updatedPet.setId("1");

    when(petRepository.updatePet(eq("1"), any(PetEntity.class))).thenReturn(updatedPet); // Modificare aici

    mockMvc.perform(put("/pets/1")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedPet)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.species").value("Cat"));
}

    @Test
    public void testDeletePet() throws Exception {
        doNothing().when(petRepository).deletePet("1");

        mockMvc.perform(delete("/pets/1")
                .contentType("application/json"))
                .andExpect(status().isOk());

        verify(petRepository, times(1)).deletePet("1");
    }
}