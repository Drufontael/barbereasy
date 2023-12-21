package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.ManagerDTO;
import tech.drufontael.BarberEasy.model.Manager;
import tech.drufontael.BarberEasy.service.ManagerService;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @PostMapping("/")
    public ResponseEntity<Manager> save(@RequestBody ManagerDTO obj){
        var manager=new Manager();
        BeanUtils.copyProperties(obj,manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(manager));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manager not found with Id: "+id);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Manager>> findAll(){
        List<Manager> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody ManagerDTO obj){
        var manager=new Manager();
        BeanUtils.copyProperties(obj,manager);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id,manager));
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Manager deleted");
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
