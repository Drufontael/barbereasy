package tech.drufontael.BarberEasy.dto;

import java.util.UUID;

public record ManagerDTO(UUID id, String username, String email, String password) {
}
