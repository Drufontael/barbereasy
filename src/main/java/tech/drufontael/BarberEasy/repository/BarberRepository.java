package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Barber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BarberRepository extends JpaRepository<Barber, UUID> {

    List<Barber> findAll();
    Optional<Barber> findByUsername(String username);
}
