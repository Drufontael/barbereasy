package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.ReservationDTO;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.service.ReservationService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;


    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @PostMapping("/create")
    public ResponseEntity<Reservation> save(@RequestBody ReservationDTO obj){

        Reservation reservation = service.fromDTO(obj);
        service.save(reservation);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/")
    public ResponseEntity<List<Reservation>> findAll(){
        List<Reservation> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/barber/{id}")
    public ResponseEntity<List<Reservation>> findByBarberId(@PathVariable Long id){
        List<Reservation> list=service.findByBarberId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Reservation>> findByCustomerId(@PathVariable Long id){
        List<Reservation> list=service.findByCustomerId(id);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id,@RequestBody ReservationDTO source){
        Reservation obj=service.update(id,source);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }




}
