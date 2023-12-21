package tech.drufontael.BarberEasy.dto;

import tech.drufontael.BarberEasy.model.Barber;


public record BarberDTO(Long id, String username, String email, String password, String barberInfo) {

}

