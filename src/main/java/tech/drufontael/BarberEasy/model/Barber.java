package tech.drufontael.BarberEasy.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("barber")
public class Barber extends User{
    private String barberInfo;
    @OneToMany
    private Set<Procedure> procedures=new HashSet<>();
    @OneToMany
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
}
