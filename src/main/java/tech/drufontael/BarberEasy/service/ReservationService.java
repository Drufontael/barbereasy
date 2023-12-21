package tech.drufontael.BarberEasy.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.repository.ReservationRepository;
import tech.drufontael.BarberEasy.service.exception.ReservationException;
import tech.drufontael.BarberEasy.service.exception.UserException;
import tech.drufontael.BarberEasy.util.Util;
import tech.drufontael.BarberEasy.dto.ReservationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

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

    public void update(Reservation reservation) {
        repository.save(reservation);
    }

    public Reservation fromDTO(ReservationDTO obj){
        var reservation=new Reservation();
        reservation.setId(obj.id());
        try {
            reservation.setCustomer(customerService.findById(obj.idCustomer()));
        }catch (UserException e){
            reservation.setCustomer(null);
        }
        try {
            reservation.setBarber(barberService.findById(obj.idBarber()));
        }catch (UserException e){
            reservation.setBarber(null);
        }
        reservation.setStartTime(obj.startTime());
        reservation.setStatus(obj.status());
        for(UUID id: obj.idProcedure()){
            try {
                var procedure = procedureService.findById(id);
                reservation.getProcedures().add(procedure);
            }catch (ReservationException e){
                System.out.println("There is no process with the ID:"+id);
            }
        }
        reservation.setEndtime();
        return reservation;
    }
}
