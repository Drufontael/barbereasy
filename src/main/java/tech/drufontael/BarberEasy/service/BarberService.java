package tech.drufontael.BarberEasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.drufontael.BarberEasy.model.Availability;
import tech.drufontael.BarberEasy.model.Barber;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;
import tech.drufontael.BarberEasy.repository.BarberRepository;

import java.time.DayOfWeek;
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

    /**
     * Creates availability of 5 working days for a barber identified by the id parameter
     * from 8:00 am to 6:00 pm with a 30-minute interval between reservations.
     * @param id Barber.id
     */
    public void AvailabilityWeekInitializer(Long id) {
        Barber barber = repository.findById(id).get();
        Set<Availability> availabilities = barber.getAvailabilities();
        LocalDateTime lastSchedule = availabilities.stream()
                .map(Availability::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        Set<Availability> schedule = new HashSet<>();
        if (lastSchedule == null) lastSchedule = LocalDateTime.now();
        LocalDate inicialDate = lastSchedule.toLocalDate().plusDays(1);

        for (int i = 0; i < 5; i++) {
            if (inicialDate.getDayOfWeek() == DayOfWeek.SATURDAY || inicialDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                inicialDate = inicialDate.plusDays(1);
                i--;
                continue;
            }
            LocalDateTime inicialTime = inicialDate.atStartOfDay().plusHours(8);
            for (int l = 0; l < 20; l++) {
                LocalDateTime finalTime = inicialTime.plusMinutes(29);
                Availability availability = new Availability(inicialTime, finalTime, ReservationStatus.FREE);
                schedule.add(availability);
                inicialTime = inicialTime.plusMinutes(30);

            }
            inicialDate=inicialDate.plusDays(1);
        }
        barber.getAvailabilities().addAll(schedule);
        System.out.println(barber);
        repository.save(barber);
    }

    public Boolean AvailabilityReservation(Long id,LocalDateTime start,LocalDateTime end){
        Barber obj=repository.findById(id).orElseThrow();
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
            if(a.getStatus()!=ReservationStatus.FREE) return false;
        }
        for (Availability a:reserveComplete){
            availabilities.remove(a);
        }
        Availability before=new Availability(initTime,start.minusMinutes(1),ReservationStatus.FREE);
        Availability newAvailability = new Availability(start,end.minusMinutes(1),ReservationStatus.PENDING);
        Availability after=new Availability(end,finalTime,ReservationStatus.FREE);
        availabilities.addAll(Arrays.asList(before,newAvailability,after));
        repository.save(obj);
        return true;
    }


}
