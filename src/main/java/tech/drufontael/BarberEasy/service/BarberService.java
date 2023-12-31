package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.drufontael.BarberEasy.event.DeleteEntityEvent;
import tech.drufontael.BarberEasy.event.DeleteEntityObserver;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.repository.BarberRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BarberService implements DeleteEntityObserver {

    @Autowired
    private BarberRepository repository;
    @Autowired
    private ProcedureService procedureService;

    private final ApplicationEventPublisher eventPublisher;

    public BarberService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public Barber save(Barber obj) {

        return repository.save(obj);

    }

    public List<Barber> findAll() {
        return repository.findAll();
    }


    public void saveAll(List<Barber> barbers) {
        repository.saveAll(barbers);
    }

    public Barber findById(UUID idBarber) {
        return repository.findById(idBarber).orElseThrow(()->new UserException("Barber not found with id: " + idBarber));
    }

    public boolean verifyAvailability(UUID id, LocalDateTime time){
        Barber barber=findById(id);
        return barber.getReservations().stream().filter(x->
                x.getStartTime().isBefore(time)&& x.getEndtime().isAfter(time)).toList().isEmpty();
    }

    public Map<LocalDateTime,Boolean> singleBarberAvailability(UUID id, LocalDate date){
        LocalDateTime iterator=date.atTime(8,0);
        Map<LocalDateTime,Boolean> availabilityDay=new HashMap<>();
        for (int i=0;i<20;i++){
            Boolean availability=verifyAvailability(id,iterator);
            availabilityDay.put(iterator,availability);
            iterator=iterator.plusMinutes(30);
        }
        return availabilityDay;
    }

    public List<Barber> findByProceduresId(UUID id){
        return repository.findByProceduresId(id);
    }

    public Barber update(UUID id,Barber obj) {
        Barber target=findById(id);
        Util.copyNonNullProperties(obj,target);
        repository.save(target);
        return target;
    }

    public Barber addProcedures(UUID id, List<UUID> idProcedures) {
        Barber target=findById(id);
        Set<Procedure> procedures=new HashSet<>();
        for (UUID idProcedure:idProcedures){
            Procedure procedure=procedureService.findById(idProcedure);
            procedures.add(procedure);
        }
        target.getProcedures().addAll(procedures);
        repository.save(target);
        return target;
    }

    @Transactional
    public void delete(UUID id){
        eventPublisher.publishEvent(new DeleteEntityEvent(id));
        Barber barber=findById(id);
        barber.getProcedures().clear();
        repository.save(barber);
        repository.delete(barber);
    }


    @Override
    @EventListener
    public void onDeleteEntity(DeleteEntityEvent event) {
        UUID wantedId=event.getEntityId();
        var procedure=procedureService.findById(wantedId);
        List<Barber> barbers=repository.findByProceduresId(wantedId);
        for (Barber b:barbers){
            b.getProcedures().remove(procedure);
        }
    }
}
