package tech.drufontael.BarberEasy.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("barber")
public class Barber extends User{
    private String barberInfo;

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
}
