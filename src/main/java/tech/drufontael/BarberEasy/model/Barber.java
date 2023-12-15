package tech.drufontael.BarberEasy.model;

import jakarta.persistence.*;
import tech.drufontael.BarberEasy.DTO.BarberDTO;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("barber")
public class Barber extends User{
    private String barberInfo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name ="barber_procedures",
            joinColumns = @JoinColumn(name = "barber_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures=new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "barber_availability", joinColumns = @JoinColumn(name = "barber_id"))
    private Set<Availability> availabilities=new HashSet<>();

    public Barber() {
    }

    public Barber(Long id, String name, String email, String password, String baberInfo) {
        super(id, name, email, password);
        this.barberInfo = baberInfo;
    }

    public String getBarberInfo() {
        return barberInfo;
    }

    public void setBarberInfo(String barberInfo) {
        this.barberInfo = barberInfo;
    }

    public Set<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(Set<Procedure> procedures) {
        this.procedures = procedures;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    @Override
    public String toString() {
        return "Barber{" +
                "barberInfo='" + barberInfo + '\'' +
                ", procedures=" + procedures +
                ", availabilities=" + availabilities +
                '}';
    }

    public BarberDTO toDTO(){
        return new BarberDTO(this.getId(),this.getUsername(),this.getEmail(),this.getPassword(),this.getBarberInfo());
    }
}
