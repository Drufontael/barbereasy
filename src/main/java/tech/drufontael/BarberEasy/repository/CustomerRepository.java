package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findAll();
    Optional<Customer> findByUsername(String username);
}
