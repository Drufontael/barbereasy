package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.repository.BarberRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.util.*;

@Service
public class BarberService {

    @Autowired
    private BarberRepository repository;


    public void save(Barber obj) {
        repository.save(obj);
    }

    public List<Barber> findAll() {
        return repository.findAll();
    }

    public Barber findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() ->
                new IllegalStateException("Valor não encontrado"));
    }





    public void saveAll(List<Barber> barbers) {
        repository.saveAll(barbers);
    }

    public Barber findById(Long idBarber) {
        return repository.findById(idBarber).orElseThrow(()->new UserException("Barber not found"));
    }
}
