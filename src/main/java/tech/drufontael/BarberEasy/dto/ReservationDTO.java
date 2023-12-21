package tech.drufontael.BarberEasy.dto;

import tech.drufontael.BarberEasy.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationDTO(UUID id, UUID idCustomer, UUID idBarber, LocalDateTime startTime,
                            ReservationStatus status,List<UUID> idProcedure){
}

