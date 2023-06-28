package Calendario;

import java.time.LocalDateTime;
import java.util.UUID;

public record RepresentacionAgendable(UUID id, LocalDateTime fecha, TipoAgendable tipo) {
    public boolean esEvento(){
        return tipo.equals(TipoAgendable.EVENTO);
    }
}
