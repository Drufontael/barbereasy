package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.CustomerDTO;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.service.CustomerService;
import tech.drufontael.BarberEasy.service.exception.ReservationException;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/")
    public ResponseEntity<Customer> save(@RequestBody CustomerDTO obj){
        var customer=new Customer();
        BeanUtils.copyProperties(obj,customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with Id: "+id);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody CustomerDTO obj){
        var customer=new Customer();
        BeanUtils.copyProperties(obj,customer);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id,customer));
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer deleted");
        }catch (ReservationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
