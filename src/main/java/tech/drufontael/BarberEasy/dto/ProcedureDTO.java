package tech.drufontael.BarberEasy.dto;

import java.util.UUID;

public record ProcedureDTO(UUID id, String name, String description, Integer durationMinutes) {
}
