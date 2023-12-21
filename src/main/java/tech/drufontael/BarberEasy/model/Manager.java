package tech.drufontael.BarberEasy.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("manager")
public class Manager extends User{


    public Manager() {
    }

    public Manager(UUID id, String name, String email, String password) {
        super(id, name, email, password);
    }
}
