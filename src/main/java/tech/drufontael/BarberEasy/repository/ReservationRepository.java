package tech.drufontael.BarberEasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.drufontael.BarberEasy.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAll();
    List<Reservation> findByBarberId(UUID id);
    List<Reservation> findByCustomerId(UUID id);
}
