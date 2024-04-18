package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.service.VetService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

import java.util.List;

@RestController
@RequestMapping("/vets")
public class VetController {

    @Autowired
    private VetService vetService;

    @PostMapping
    public VetEntity createVet(@RequestBody VetEntity vet) {
        return vetService.createVet(vet);
    }

    @Timed(value = "vets.time", description = "Time taken to return all vets")
    @Counted(value = "vets.count", description = "Times all vets were returned")
    @GetMapping
    public List<VetEntity> getAllVets() {
        return vetService.getAllVets();
    }

    @GetMapping("/{id}")
    public VetEntity getVetById(@PathVariable String id) {
        return vetService.getVetById(id);
    }

    @PutMapping("/{id}")
    public VetEntity updateVet(@PathVariable String id, @RequestBody VetEntity vetDetails) {
        return vetService.updateVet(id, vetDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteVet(@PathVariable String id) {
        vetService.deleteVet(id);
    }
}
