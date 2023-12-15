package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer obj){
        service.save(obj);
        return ResponseEntity.ok("Cliente Cadastrado");
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<Customer> findByUsername(@PathVariable String username){
        Customer obj=service.findByUsername(username);
        return ResponseEntity.ok(obj);
    }


}
