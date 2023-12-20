package tech.drufontael.BarberEasy.util;
import java.util.List;

public class IdListWrapper {

    private List<Long> ids;

    public IdListWrapper() {
    }

    public IdListWrapper(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

// Métodos estáticos de criação se necessário
}

