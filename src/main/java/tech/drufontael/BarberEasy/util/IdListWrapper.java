package tech.drufontael.BarberEasy.util;
import java.util.List;
import java.util.UUID;

public class IdListWrapper {

    private List<UUID> ids;

    public IdListWrapper() {
    }

    public IdListWrapper(List<UUID> ids) {
        this.ids = ids;
    }

    public List<UUID> getIds() {
        return ids;
    }

    public void setIds(List<UUID> ids) {
        this.ids = ids;
    }

// Métodos estáticos de criação se necessário
}

