package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Manager;
import tech.drufontael.BarberEasy.repository.ManagerRepository;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private  ManagerRepository repository;

    public void save(Manager obj){
        repository.save(obj);
    }

    public List<Manager> findAll(){
        return repository.findAll();
    }

    public Manager findByUsername(String username){
        return repository.findByUsername(username).orElseThrow(()->
                new IllegalStateException("Valor não encontrado"));
    }

}
