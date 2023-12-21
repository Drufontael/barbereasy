package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.repository.ProcedureRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;

import java.util.List;
import java.util.UUID;

@Service
public class ProcedureService {

    @Autowired
    private  ProcedureRepository repository;

    public Procedure save(Procedure obj){
        return repository.save(obj);
    }

    public List<Procedure> findAll(){
        return repository.findAll();
    }

    public Procedure findById(UUID idProcedure) {
        return repository.findById(idProcedure).orElseThrow(()->new UserException("Procedure not found with id:"+idProcedure) );
    }

    public Object update(UUID id, Procedure source) {
        var target=findById(id);
        Util.copyNonNullProperties(source,target);
        return repository.save(target);
    }

    @Transactional
    public void delete(UUID id) {
        var obj=findById(id);
        repository.delete(obj);
    }
}
