package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;

@RestController
@RequestMapping("/vets")
public class VetController {
    @Autowired
    private VetRepository vetRepository;

    @PostMapping
    public VetEntity createVet(@RequestBody VetEntity vet) {
        return vetRepository.save(vet);
    }

    @GetMapping
    public List<VetEntity> getAllVets() {
        return vetRepository.findAll();
    }

    @GetMapping("/{id}")
    public VetEntity getVetById(@PathVariable String id) {
        return vetRepository.findById(id).orElse(null);
    }

        @PutMapping("/{id}")
    public VetEntity updateVet(@PathVariable String id, @RequestBody VetEntity vetDetails) {
        VetEntity vet = vetRepository.findById(id).orElseThrow(() -> new RuntimeException("Vet not found with id: " + id));
        vet.setName(vetDetails.getName());
        vet.setSpecialization(vetDetails.getSpecialization());
        return vetRepository.save(vet);
    }

    @DeleteMapping("/{id}")
    public void deleteVet(@PathVariable String id) {
        vetRepository.deleteById(id);
    }
}
