package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.drufontael.BarberEasy.model.Manager;
import tech.drufontael.BarberEasy.repository.ManagerRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;

import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    private  ManagerRepository repository;

    public Manager save(Manager obj){
        return repository.save(obj);
    }

    public List<Manager> findAll(){
        return repository.findAll();
    }

    public Manager findById(UUID idManager) {
        return repository.findById(idManager).orElseThrow(()->new UserException("Manager not found with id:"+idManager) );
    }

    public Object update(UUID id, Manager source) {
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
