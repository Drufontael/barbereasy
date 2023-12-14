package tech.drufontael.BarberEasy.dto;

import tech.drufontael.BarberEasy.model.Barber;

public record BarberDTO(Long id,String name, String email, String password, String barberInfo) {
    public Barber toBarber(){
        return new Barber(id,name,email,password,barberInfo);
    }
}
