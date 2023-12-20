package tech.drufontael.BarberEasy.controller;

import jakarta.websocket.server.PathParam;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private BarberService service;

    // C R U D

    //Create
    @PostMapping("/create")
    public ResponseEntity<BarberDTO> save(@RequestBody BarberDTO obj){
        return ResponseEntity.status(201).body(service.save(obj.toBarber()).toDTO());
    }

    //Read
    @GetMapping("/{id}")
    public  ResponseEntity<Barber> read(@PathVariable Long id){
        Barber barber=service.findById(id);
        return ResponseEntity.ok(barber);
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

    @GetMapping("/availability/{id}")
    public ResponseEntity<Map<LocalDateTime,Boolean>> singleBarberAvailability(@PathVariable Long id,@PathParam("date") LocalDate date){
        Map<LocalDateTime,Boolean> obj=service.singleBarberAvailability(id,date);
        return ResponseEntity.ok(obj);

    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Barber> update(@PathVariable Long id,@RequestBody BarberDTO obj){

        Barber barber=service.update(id,obj);
        return ResponseEntity.status(201).body(barber);
    }

    @PutMapping("/procedure/{id}")
    public ResponseEntity<Barber> addProcedure(@PathVariable Long id,@RequestBody IdListWrapper idListWrapper){
        List<Long> idProcedures = idListWrapper.getIds();
        Barber barber=service.addProcedures(id,idProcedures);
        return ResponseEntity.status(201).body(barber);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
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
