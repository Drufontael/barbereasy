package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {

    List<Manager> findAll();
    Optional<Manager> findByUsername(String username);
}
