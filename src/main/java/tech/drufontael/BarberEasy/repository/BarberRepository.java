package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.User;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber,Long> {

    List<Barber> findAll();
    Optional<Barber> findByUsername(String username);
}
