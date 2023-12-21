package tech.drufontael.BarberEasy.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User{
    private String customerInfo;
    @OneToMany(mappedBy = "customer")
    private final Set<Reservation> reservations=new HashSet<>();

    public Customer() {
    }

    public Customer(UUID id, String username, String email, String password, String customerInfo) {
        super(id, username, email, password);
        this.customerInfo = customerInfo;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }
}
