package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.DTO.BarberDTO;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.service.BarberService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private BarberService service;

    @PostMapping("/save")
    public ResponseEntity<BarberDTO> save(@RequestBody BarberDTO obj){

        service.save(obj.toBarber());
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/")
    public ResponseEntity<List<BarberDTO>> findAll(){
        List<Barber> listBarber=service.findAll();
        List<BarberDTO> list=new ArrayList<>();
        for (Barber barber:listBarber){
            list.add(barber.toDTO());
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<BarberDTO> findByUsername(@PathVariable String username){
        Barber obj=service.findByUsername(username);
        return ResponseEntity.ok(obj.toDTO());
    }
}
