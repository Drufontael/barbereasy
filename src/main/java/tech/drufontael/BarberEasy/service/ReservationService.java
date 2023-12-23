package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import tech.drufontael.BarberEasy.event.DeleteEntityEvent;
import tech.drufontael.BarberEasy.event.DeleteEntityObserver;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;
import tech.drufontael.BarberEasy.repository.ReservationRepository;
import tech.drufontael.BarberEasy.service.exception.ReservationException;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;
import tech.drufontael.BarberEasy.dto.ReservationDTO;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService implements DeleteEntityObserver {

    @Autowired
    private  ReservationRepository repository;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BarberService barberService;
    @Autowired
    private ProcedureService procedureService;

    public Reservation save(Reservation obj){
        obj.setEndtime();
        return repository.save(obj);
    }

    public List<Reservation> findAll(){
        return repository.findAll();
    }


    public void saveAll(List<Reservation> list) {
        for (Reservation r:list){
            r.setEndtime();
        }
        repository.saveAll(list);
    }

    public List<Reservation> findByBarberId(UUID id){
        return repository.findByBarberId(id);
    }

    public List<Reservation> findByCustomerId(UUID id){
        return repository.findByCustomerId(id);
    }

    public List<Reservation> findByProceduresId(UUID id){
        return repository.findByProceduresId(id);
    }


    public Reservation update(UUID id, Reservation source){
        var reservation=repository.findById(id).orElseThrow(()->new ReservationException("Reservation not found with id: "+id));
        Util.copyNonNullProperties(source,reservation);
        return repository.save(reservation);
    }

    public boolean delete(UUID id){
        if(!repository.findById(id).isPresent()) return false;
        Reservation reservation=repository.findById(id).get();
        repository.delete(reservation);
        return true;
    }


    public Reservation fromDTO(ReservationDTO obj){
        var reservation=new Reservation();
        reservation.setId(obj.id());
        if(obj.idCustomer()!=null)
        {
            reservation.setCustomer(customerService.findById(obj.idCustomer()));
        }
        if(obj.idBarber()!=null){
            reservation.setBarber(barberService.findById(obj.idBarber()));
        }
        reservation.setStartTime(obj.startTime());
        reservation.setStatus(obj.status());
        if(obj.idProcedure()!=null){
            for (UUID id : obj.idProcedure()) {
                try {
                    var procedure = procedureService.findById(id);
                    reservation.getProcedures().add(procedure);
                } catch (ReservationException e) {
                    System.out.println("There is no process with the ID:" + id);
                }
            }
        }
        reservation.setEndtime();
        reservation.setValue();
        return reservation;
    }

    @Override
    @EventListener
    public void onDeleteEntity(DeleteEntityEvent event) {
        UUID wantedId=event.getEntityId();
        List<Reservation> isCustomer=repository.findByCustomerId(wantedId);
        List<Reservation> isBarber=repository.findByBarberId(wantedId);
        List<Reservation> isProcedure=repository.findByProceduresId(wantedId);
        if(!isCustomer.isEmpty()){
            for (Reservation r:isCustomer){
                if(r.getStatus().equals(ReservationStatus.CANCELED)||r.getStatus().equals(ReservationStatus.COMPLETED)){
                    r.setCustomer(null);
                }else throw new ReservationException("Customer with id "+wantedId+" have pending reservation");
                repository.save(r);
            }
        }
        if(!isBarber.isEmpty()){
            for (Reservation r:isBarber){
                if(r.getStatus().equals(ReservationStatus.CANCELED)||r.getStatus().equals(ReservationStatus.COMPLETED)){
                    r.setBarber(null);
                }else throw new ReservationException("Barber with id "+wantedId+" have pending reservations");
                repository.save(r);
            }
        }
        if(!isProcedure.isEmpty()){
            for (Reservation r:isProcedure){
                var procedure=procedureService.findById(wantedId);
                if(r.getStatus().equals(ReservationStatus.CANCELED)||r.getStatus().equals(ReservationStatus.COMPLETED)){
                    r.getProcedures().remove(procedure);
                }else throw new ReservationException("Procedure with the id "+wantedId+" is in an open reservation.");
                repository.save(r);
            }
        }
    }

    public Reservation findById(UUID id) {
        return repository.findById(id).orElseThrow(()->new ReservationException("Reservation not found with id:"+id));
    }
}
