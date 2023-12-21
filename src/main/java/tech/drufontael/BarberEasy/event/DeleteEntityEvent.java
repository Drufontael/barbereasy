package tech.drufontael.BarberEasy.event;

import java.util.UUID;


public class DeleteEntityEvent {
    private final UUID entityId;

    public DeleteEntityEvent(UUID entityId) {
        this.entityId = entityId;
    }

    public UUID getEntityId() {
        return entityId;
    }
}

