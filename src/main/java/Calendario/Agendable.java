package Calendario;

import java.util.UUID;

public interface Agendable {
    void aceptar(VisitorAgendable visitor);

    UUID getId();
}
