package GUI;

import Calendario.Calendario;
import Calendario.Evento;
import Calendario.Tarea;
import Calendario.VisitorAgendable;

public class VisitorEliminarAgendable implements VisitorAgendable {
    private Calendario calendario;

    public VisitorEliminarAgendable(Calendario calendario) {
        this.calendario = calendario;
    }

    @Override
    public void visitarEvento(Evento evento) {
        this.calendario.eliminarEvento(evento.getId());
    }

    @Override
    public void visitarTarea(Tarea tarea) {
        this.calendario.eliminarTarea(tarea.getId());
    }
}
