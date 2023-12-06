package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.service.BarberService;

import java.util.List;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private BarberService service;

    @PostMapping("/save")
    public ResponseEntity<Barber> save(@RequestBody Barber obj){
        service.save(obj);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/")
    public ResponseEntity<List<Barber>> findAll(){
        List<Barber> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<Barber> findByUsername(@PathVariable String username){
        Barber obj=service.findByUsername(username);
        return ResponseEntity.ok(obj);
    }
}
