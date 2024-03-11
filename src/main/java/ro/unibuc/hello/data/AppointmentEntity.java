package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
public class AppointmentEntity {

    @Id
    private String id;

    @DBRef
    private PetEntity pet;

    @DBRef
    private VetEntity vet;

    private LocalDateTime appointmentTime;
    private String reason;

    public AppointmentEntity() {}

    public AppointmentEntity(PetEntity pet, VetEntity vet, LocalDateTime appointmentTime, String reason) {
        this.pet = pet;
        this.vet = vet;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    // Getters
    public String getId() {
        return id;
    }

    public PetEntity getPet() {
        return pet;
    }

    public VetEntity getVet() {
        return vet;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public void setVet(VetEntity vet) {
        this.vet = vet;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // toString
    @Override
    public String toString() {
        return "AppointmentEntity{" +
               "id='" + id + '\'' +
               ", pet=" + pet +
               ", vet=" + vet +
               ", appointmentTime=" + appointmentTime +
               ", reason='" + reason + '\'' +
               '}';
    }
}
