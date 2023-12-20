package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.drufontael.BarberEasy.dto.BarberDTO;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;
import tech.drufontael.BarberEasy.repository.BarberRepository;
import tech.drufontael.BarberEasy.repository.ProcedureRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BarberService {

    @Autowired
    private BarberRepository repository;
    @Autowired
    private ProcedureService procedureService;



    public Barber save(Barber obj) {

        return repository.save(obj);

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

    public Barber update(Long id,BarberDTO obj) {
        Barber source=obj.toBarber();
        Barber target=findById(id);
        Util.copyNonNullProperties(source,target);
        repository.save(target);
        return target;
    }

    public Barber addProcedures(Long id, List<Long> idProcedures) {
        Barber target=findById(id);
        Set<Procedure> procedures=new HashSet<>();
        for (Long idProcedure:idProcedures){
            Procedure procedure=procedureService.findById(idProcedure);
            procedures.add(procedure);
        }
        target.getProcedures().addAll(procedures);
        repository.save(target);
        return target;
    }

    @Transactional
    public Boolean delete(Long id){
        Barber barber=findById(id);
        barber.getProcedures().clear();
        repository.save(barber);
        repository.delete(barber);
        return true;
    }


}
