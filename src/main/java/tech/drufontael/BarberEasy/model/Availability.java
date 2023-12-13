package tech.drufontael.BarberEasy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;

import java.time.LocalDateTime;
@Entity(name = "tb_availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
}
