package tech.drufontael.BarberEasy.dto;

import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationDTO(Long id, Customer customer, Barber barber, LocalDateTime dateTime,
                             ReservationStatus status, List<Procedure> procedureList) {
    public Reservation toReservation(){
        Reservation reservation=new Reservation(id,customer,barber,dateTime,status);
        reservation.getProcedures().addAll(procedureList);
        return reservation;
    }

}
