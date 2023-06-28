package Calendario;

public interface VisitorAgendable {
    void visitarEvento(Evento evento);
    void visitarTarea(Tarea tarea);
}
