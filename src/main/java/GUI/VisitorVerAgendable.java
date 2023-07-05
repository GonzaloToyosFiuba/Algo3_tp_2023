package GUI;

import Calendario.Evento;
import Calendario.Tarea;
import Calendario.VisitorAgendable;

import java.time.LocalDateTime;

public class VisitorVerAgendable implements VisitorAgendable {
    ControladorVentanaInfo controlador;
    LocalDateTime fecha;

    public VisitorVerAgendable(ControladorVentanaInfo controlador, LocalDateTime fecha) {
        this.controlador = controlador;
        this.fecha = fecha;
    }

    @Override
    public void visitarEvento(Evento evento) {
        controlador.mostrarInformacionEvento(evento, this.fecha);
    }

    @Override
    public void visitarTarea(Tarea tarea) {
        controlador.mostrarInformacionTarea(tarea, this.fecha);
    }
}
