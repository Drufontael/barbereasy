package tech.drufontael.BarberEasy.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User{
    private String customerInfo;

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
