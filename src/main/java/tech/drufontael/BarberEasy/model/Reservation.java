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
    @JoinColumn(name="id_barber")
    private Barber barber;

    @ManyToMany
    @JoinTable(
            name = "tb_reservation_procedures",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures = new HashSet<>();
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(Long id, Customer customer, Barber barber, LocalDateTime dateTime, ReservationStatus status) {
        this.id = id;
        this.customer = customer;
        this.barber = barber;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Integer getDuration(){
        Integer duration=this.procedures.stream().map(Procedure::getDurationMinutes).reduce((a,b)->a+b).orElse(null);
        return duration;
    }
}
