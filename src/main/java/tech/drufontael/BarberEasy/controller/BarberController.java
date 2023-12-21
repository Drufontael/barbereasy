package tech.drufontael.BarberEasy.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.BarberDTO;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.service.BarberService;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.IdListWrapper;
import tech.drufontael.BarberEasy.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private BarberService service;

    // C R U D

    //Create
    @PostMapping("/create")
    public ResponseEntity<Barber> save(@RequestBody BarberDTO obj){
        var barber=new Barber();
        Util.copyNonNullProperties(obj,barber);
        return ResponseEntity.status(201).body(service.save(barber));
    }

    //Read
    @GetMapping("/{id}")
    public  ResponseEntity<Object> findById(@PathVariable UUID id){
        try{
            Barber barber = service.findById(id);
            return ResponseEntity.ok(barber);
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll(){
        List<Barber> listBarber=service.findAll();
        if(listBarber.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("List is empty");
        return ResponseEntity.ok(listBarber);
    }


    @GetMapping("/availability/{id}")
    public ResponseEntity<Map<LocalDateTime,Boolean>> singleBarberAvailability(@PathVariable UUID id, @PathParam("date") LocalDate date){
        Map<LocalDateTime,Boolean> obj=service.singleBarberAvailability(id,date);
        return ResponseEntity.ok(obj);
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Barber> update(@PathVariable UUID id,@RequestBody BarberDTO obj){
        var barber=new Barber();
        Util.copyNonNullProperties(obj,barber);
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id,barber));
    }

    @PutMapping("/procedure/{id}")
    public ResponseEntity<Barber> addProcedure(@PathVariable UUID id,@RequestBody IdListWrapper idListWrapper){
        List<UUID> idProcedures = idListWrapper.getIds();
        Barber barber=service.addProcedures(id,idProcedures);
        return ResponseEntity.status(201).body(barber);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        try {
            service.delete(id);
            return ResponseEntity.status(204).body("Barbeiro excluido com sucesso!");
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Barbeiro possui reservas em processamento, não pode ser excluido.");
        }catch (UserException e){
            return ResponseEntity.status(404).body("Barbeiro não encontrado, verifique o id.");
        }
    }

}
