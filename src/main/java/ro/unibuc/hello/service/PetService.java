package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.PetRepository;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public PetEntity createPet(PetEntity pet) {
        return petRepository.save(pet);
    }

    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    public PetEntity getPetById(String id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    }

    public PetEntity updatePet(String id, PetEntity petDetails) {
        PetEntity pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        pet.setName(petDetails.getName());
        pet.setSpecies(petDetails.getSpecies());
        return petRepository.save(pet); // Returnează entitatea actualizată
    }
    

    public void deletePet(String id) {
        petRepository.deleteById(id);
    }
}
