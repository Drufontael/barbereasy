package tech.drufontael.BarberEasy.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User{
    private String customerInfo;
    @OneToMany(mappedBy = "customer")
    private Set<Reservation> reservations=new HashSet<>();

    public Customer() {
    }

    public Customer(Long id, String name, String email, String password, String customerInfo) {
        super(id, name, email, password);
        this.customerInfo = customerInfo;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }
}
