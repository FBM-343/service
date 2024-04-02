package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;

import java.util.List;

@Service
public class VetService {

    @Autowired
    private VetRepository vetRepository;

    public VetEntity createVet(VetEntity vet) {
        return vetRepository.save(vet);
    }

    public List<VetEntity> getAllVets() {
        return vetRepository.findAll();
    }

    public VetEntity getVetById(String id) {
        return vetRepository.findById(id).orElse(null);
    }

    public VetEntity updateVet(String id, VetEntity vetDetails) {
        VetEntity vet = vetRepository.findById(id).orElseThrow(() -> new RuntimeException("Vet not found with id: " + id));
        vet.setName(vetDetails.getName());
        vet.setSpecialization(vetDetails.getSpecialization());
        return vetRepository.save(vet); // Returnează entitatea actualizată
    }
    

    public void deleteVet(String id) {
        vetRepository.deleteById(id);
    }
}
