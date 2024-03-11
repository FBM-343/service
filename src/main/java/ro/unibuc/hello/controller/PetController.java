package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.PetRepository;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @PostMapping
    public PetEntity createPet(@RequestBody PetEntity pet) {
        return petRepository.save(pet);
    }

    @GetMapping
    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    @GetMapping("/{id}")
    public PetEntity getPetById(@PathVariable String id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
    }

    @PutMapping("/{id}")
    public PetEntity updatePet(@PathVariable String id, @RequestBody PetEntity petDetails) {
        PetEntity pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        pet.setName(petDetails.getName());
        pet.setSpecies(petDetails.getSpecies());
        return petRepository.save(pet);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable String id) {
        petRepository.deleteById(id);
    }
}
