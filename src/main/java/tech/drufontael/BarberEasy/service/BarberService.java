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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


}
