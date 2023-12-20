package tech.drufontael.BarberEasy.model;

import jakarta.persistence.*;
import tech.drufontael.BarberEasy.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "id_barber")
    private Barber barber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_reservation_procedures",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures = new HashSet<>();
    private LocalDateTime startTime;
    private LocalDateTime endtime;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(Long id, Customer customer, Barber barber, LocalDateTime startTime, ReservationStatus status) {
        this.id = id;
        this.customer = customer;
        this.barber = barber;
        this.startTime = startTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public Set<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(Set<Procedure> procedures) {
        this.procedures = procedures;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public void setEndtime() {
        if(getDuration()!=null){
            this.endtime = startTime.plusMinutes(getDuration() - 1);
        } else {
            endtime=startTime.plusMinutes(30);
        }
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Integer getDuration(){
        return this.procedures.stream().map(Procedure::getDurationMinutes).reduce((a, b)->a+b).orElse(null);
    }
}
