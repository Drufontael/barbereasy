package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Customer;
import tech.drufontael.BarberEasy.model.Procedure;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.repository.ReservationRepository;
import tech.drufontael.BarberEasy.service.exception.ReservationException;
import tech.drufontael.BarberEasy.util.Util;
import tech.drufontael.BarberEasy.dto.ReservationDTO;

import java.util.ArrayList;
import java.util.List;

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

    public void save(Reservation obj){
        obj.setEndtime();
        repository.save(obj);
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

    public List<Reservation> findByBarberId(Long id){
        return repository.findByBarberId(id);
    }

    public List<Reservation> findByCustomerId(Long id){
        return repository.findByCustomerId(id);
    }

    public Reservation fromDTO(ReservationDTO obj){

        Customer customer = customerService.findById(obj.getIdCustomer());
        Barber barber = barberService.findById(obj.getIdBarber());
        List<Procedure> procedures = new ArrayList<>();
        for (Long id : obj.getProceduresId()) {
            Procedure procedure = procedureService.findById(id);
            if (barber.getProcedures().contains(procedure)) {
                procedures.add(procedure);
            } else {
                throw new ReservationException("Barber does not perform this procedure");
            }
        }
        Reservation reservation = new Reservation(obj.getId(), customer, barber, obj.getStartTime(), obj.getStatus());
        reservation.getProcedures().addAll(procedures);
        reservation.setEndtime();
        return reservation;
    }
    public ReservationDTO toDTO(Reservation source){
        List<Long> idProcedures=source.getProcedures().stream().map(x->x.getId()).toList();
        return new ReservationDTO(source.getId(),source.getCustomer().getId(),source.getBarber().getId(),source.getStartTime(),source.getStatus()
        ,idProcedures);
    }

    public Reservation update(Long id, ReservationDTO source){
        Reservation reservation=repository.findById(id).orElseThrow(()->new ReservationException("Reservation not found"));
        ReservationDTO target=this.toDTO(reservation);
        Util.copyNonNullProperties(source,target);
        reservation=this.fromDTO(target);
        repository.save(reservation);
        return reservation;
    }

    public boolean delete(Long id){
        if(!repository.findById(id).isPresent()) return false;
        Reservation reservation=repository.findById(id).get();
        repository.delete(reservation);
        return true;
    }
}
