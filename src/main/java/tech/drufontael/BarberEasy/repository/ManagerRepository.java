package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

    List<Manager> findAll();
    Optional<Manager> findByUsername(String username);
}
