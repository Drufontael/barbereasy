package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.ReservationDTO;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.service.ReservationService;
import tech.drufontael.BarberEasy.service.exception.ReservationException;
import tech.drufontael.BarberEasy.util.Util;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;


    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @PostMapping("/")
    public ResponseEntity<Reservation> save(@RequestBody ReservationDTO sourceDTO){
        var reservation=new Reservation();
        var source=service.fromDTO(sourceDTO);
        Util.copyNonNullProperties(source,reservation);
        service.save(reservation);
        return ResponseEntity.ok(service.save(reservation));
    }

    @GetMapping("/")
    public ResponseEntity<List<Reservation>> findAll(){
        List<Reservation> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/barber/{id}")
    public ResponseEntity<List<Reservation>> findByBarberId(@PathVariable UUID id){
        List<Reservation> list=service.findByBarberId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Reservation>> findByCustomerId(@PathVariable UUID id){
        List<Reservation> list=service.findByCustomerId(id);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody ReservationDTO sourceDTO){
        var reservation=new Reservation();
        var source=service.fromDTO(sourceDTO);
        Util.copyNonNullProperties(source,reservation);
        try{
            return ResponseEntity.ok(service.update(id, reservation));
        }catch (ReservationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id){
        return ResponseEntity.ok(service.delete(id));
    }




}
