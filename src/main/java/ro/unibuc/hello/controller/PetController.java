package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.service.PetService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetEntity createPet(@RequestBody PetEntity pet) {
        return petService.createPet(pet);
    }

    @Timed(value = "pets.time", description = "Time taken to return all pets")
    @Counted(value = "pets.count", description = "Times all pets were returned")
    @GetMapping
    public List<PetEntity> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public PetEntity getPetById(@PathVariable String id) {
        return petService.getPetById(id);
    }

    @PutMapping("/{id}")
    public PetEntity updatePet(@PathVariable String id, @RequestBody PetEntity petDetails) {
        return petService.updatePet(id, petDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable String id) {
        petService.deletePet(id);
    }
}