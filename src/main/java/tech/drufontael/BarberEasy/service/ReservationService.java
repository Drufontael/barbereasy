package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Availability;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.Reservation;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;
import tech.drufontael.BarberEasy.repository.BarberRepository;
import tech.drufontael.BarberEasy.repository.ReservationRepository;
import tech.drufontael.BarberEasy.service.exception.UserException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService {

    @Autowired
    private  ReservationRepository repository;
    @Autowired
    private BarberRepository barberRepository;

    public void save(Reservation obj){
        repository.save(obj);
    }

    public List<Reservation> findAll(){
        return repository.findAll();
    }

    public Boolean AvailabilityReservation(Long id, LocalDateTime start, LocalDateTime end){
        Barber obj=barberRepository.findById(id).orElseThrow(()->new UserException("Barber not found"));
        Set<Availability> availabilities= obj.getAvailabilities();
        Availability first=availabilities.stream()
                .filter(x->(x.getStartTime().equals(start)||x.getStartTime().isBefore(start))
                        &&(x.getEndTime().isAfter(start))).findFirst().orElseThrow();
        Availability last=availabilities.stream()
                .filter(x->(x.getStartTime().isBefore(end)||x.getStartTime().equals(end))
                        &&(x.getEndTime().isAfter(end)||x.getEndTime().equals(end))).findFirst().orElseThrow();
        List<Availability> reserveComplete= availabilities.stream()
                .filter(x->x.getStartTime().equals(first.getStartTime()) || (x.getStartTime().isAfter(first.getStartTime()))
                        && (x.getEndTime().equals(last.getEndTime()) || x.getEndTime().isBefore(last.getEndTime())))
                .toList();
        LocalDateTime initTime=first.getStartTime();
        LocalDateTime finalTime=last.getEndTime();
        for (Availability a:reserveComplete){
            if(a.getStatus()!= ReservationStatus.FREE) return false;
        }
        for (Availability a:reserveComplete){
            availabilities.remove(a);
        }
        Availability before=new Availability(initTime,start.minusMinutes(1),ReservationStatus.FREE);
        Availability newAvailability = new Availability(start,end.minusMinutes(1),ReservationStatus.PENDING);
        Availability after=new Availability(end,finalTime,ReservationStatus.FREE);
        availabilities.addAll(Arrays.asList(before,newAvailability,after));
        barberRepository.save(obj);
        return true;
    }

}
