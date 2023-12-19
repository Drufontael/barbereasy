package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.ReservationDTO;
import tech.drufontael.BarberEasy.model.Availability;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.service.ReservationService;
import tech.drufontael.BarberEasy.service.exception.ReservationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ReservationDTO obj){
        Reservation reservation=obj.toReservation();
        LocalDateTime endTime=reservation.getDateTime().plusMinutes(reservation.getDuration());
        if(service.AvailabilityReservation(reservation.getBarber().getId(),reservation.getDateTime(),endTime)){
            service.save(reservation);
        }else throw new ReservationException("Unable to make reservation");
        return ResponseEntity.ok("Registered reservation");
    }

    @GetMapping("/")
    public ResponseEntity<List<Reservation>> findAll(){
        List<Reservation> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Availability>> verifyAvailability(@PathVariable String date){
        LocalDate localDate=LocalDate.parse(date,dtf);
        List<Availability> list=service.dayAvailability(localDate);
        return ResponseEntity.ok(list);

    }

   


}
