package tech.drufontael.BarberEasy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    @OneToMany(mappedBy = "barber", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Reservation> reservations =new HashSet<>();

    public Barber() {
    }

    public Barber(UUID id, String name, String email, String password, String barberInfo) {
        super(id, name, email, password);
        this.barberInfo = barberInfo;
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

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Barber{" +
                "barberInfo='" + barberInfo + '\'' +
                ", procedures=" + procedures +
                ", availabilities=" + reservations +
                '}';
    }

}
