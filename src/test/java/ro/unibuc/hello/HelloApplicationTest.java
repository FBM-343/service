package ro.unibuc.hello;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.*;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class HelloApplicationTest {

    @Mock
    private InformationRepository informationRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private HelloApplication helloApplication;

    @Test
    public void testRunAfterObjectCreated() {
        // Call the post construct method manually
        helloApplication.runAfterObjectCreated();

        // Verify that deleteAll and save/saveAll methods are called on repositories
        verify(informationRepository).deleteAll();
        verify(informationRepository).save(any(InformationEntity.class));

        verify(petRepository).deleteAll();
        verify(petRepository).saveAll(any(List.class));

        verify(vetRepository).deleteAll();
        verify(vetRepository).saveAll(any(List.class));

        verify(appointmentRepository).deleteAll();
        verify(appointmentRepository).saveAll(any(List.class));
    }
}
