package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.repository.ProcedureRepository;
import tech.drufontael.BarberEasy.service.exception.ReservationException;

import java.util.List;

@Service
public class ProcedureService {

    @Autowired
    private  ProcedureRepository repository;

    public void save(Procedure obj){
        repository.save(obj);
    }

    public List<Procedure> findAll(){
        return repository.findAll();
    }


    public Procedure findById(Long id) {
        return repository.findById(id).orElseThrow(()->new ReservationException("Procedure not found!"));
    }


}
