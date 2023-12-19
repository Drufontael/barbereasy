package tech.drufontael.BarberEasy.dto;

import tech.drufontael.BarberEasy.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {

    private Long id;
    private Long idCustomer;
    private Long idBarber;
    private LocalDateTime startTime;
    private ReservationStatus status;
    private List<Long> proceduresId;

    public ReservationDTO(Long id, Long idCustomer, Long idBarber, LocalDateTime startTime,
                          ReservationStatus status, List<Long> proceduresId) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idBarber = idBarber;
        this.startTime = startTime;
        this.status = status;
        this.proceduresId = proceduresId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdBarber() {
        return idBarber;
    }

    public void setIdBarber(Long idBarber) {
        this.idBarber = idBarber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public List<Long> getProceduresId() {
        return proceduresId;
    }

    public void setProceduresId(List<Long> proceduresId) {
        this.proceduresId = proceduresId;
    }
}

