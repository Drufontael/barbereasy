package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.model.Manager;
import tech.drufontael.BarberEasy.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @PostMapping("/save")
    public ResponseEntity<Manager> save(@RequestBody Manager obj){
        service.save(obj);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/")
    public ResponseEntity<List<Manager>> findAll(){
        List<Manager> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<Manager> findByUsername(@PathVariable String username){
        Manager obj=service.findByUsername(username);
        return ResponseEntity.ok(obj);
    }
}
