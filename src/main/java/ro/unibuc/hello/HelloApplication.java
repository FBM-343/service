package ro.unibuc.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import ro.unibuc.hello.data.PetEntity;
import ro.unibuc.hello.data.PetRepository;
import ro.unibuc.hello.data.VetEntity;
import ro.unibuc.hello.data.VetRepository;
import ro.unibuc.hello.data.AppointmentEntity;
import ro.unibuc.hello.data.AppointmentRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class, PetRepository.class, VetRepository.class, AppointmentRepository.class})
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        // Curățăm bazele de date
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
								"This is an example of using a date storage engine running separately from our application server"));
        petRepository.deleteAll();
        vetRepository.deleteAll();
        appointmentRepository.deleteAll();

        // Creăm și salvăm câteva entități Pet
        PetEntity pet1 = new PetEntity("Rex", "Câine");
        PetEntity pet2 = new PetEntity("Whiskers", "Pisică");
        petRepository.saveAll(List.of(pet1, pet2));

        // Creăm și salvăm câteva entități Vet
        VetEntity vet1 = new VetEntity("Dr. Smith", "Medicina veterinara interna");
        VetEntity vet2 = new VetEntity("Dr. Jones", "Chirurgie veterinara");
        vetRepository.saveAll(List.of(vet1, vet2));

        // Creăm și salvăm câteva entități Appointment
        AppointmentEntity appointment1 = new AppointmentEntity(pet1, vet1, LocalDateTime.now().plusDays(1), "Control anual");
        AppointmentEntity appointment2 = new AppointmentEntity(pet2, vet2, LocalDateTime.now().plusDays(2), "Consult de urgenta");
        appointmentRepository.saveAll(List.of(appointment1, appointment2));
    }
}
