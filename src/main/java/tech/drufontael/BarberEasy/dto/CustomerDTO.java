package tech.drufontael.BarberEasy.dto;

import java.util.UUID;

public record CustomerDTO(UUID id, String username, String email, String password, String customerInfo) {
}
