package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.repository.BarberRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean verifyAvailability(Long id, LocalDateTime time){
        Barber barber=findById(id);
        return barber.getReservations().stream().filter(x->
                x.getStartTime().isBefore(time)&& x.getEndtime().isAfter(time)).toList().isEmpty();
    }

    public Map<LocalDateTime,Boolean> singleBarberAvailability(Long id, LocalDate date){
        LocalDateTime iterator=date.atTime(8,0);
        Map<LocalDateTime,Boolean> availabilityDay=new HashMap<>();
        for (int i=0;i<20;i++){
            Boolean availability=verifyAvailability(id,iterator);
            availabilityDay.put(iterator,availability);
            iterator=iterator.plusMinutes(30);
        }
        return availabilityDay;
    }
}
