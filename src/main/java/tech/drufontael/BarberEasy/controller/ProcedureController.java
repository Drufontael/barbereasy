package tech.drufontael.BarberEasy.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.drufontael.BarberEasy.dto.ProcedureDTO;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.service.ProcedureService;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureService service;

    @PostMapping("/")
    public ResponseEntity<Procedure> save(@RequestBody ProcedureDTO obj){
        var procedure=new Procedure();
        BeanUtils.copyProperties(obj,procedure);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(procedure));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Procedure not found with Id: "+id);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Procedure>> findAll(){
        List<Procedure> list=service.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody ProcedureDTO obj){
        var procedure=new Procedure();
        BeanUtils.copyProperties(obj,procedure);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id,procedure));
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Procedure deleted");
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (UserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
