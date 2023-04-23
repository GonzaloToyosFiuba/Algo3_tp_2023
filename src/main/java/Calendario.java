import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario {
    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;
    private ArrayList<Alarma> alarmasProximas;

    public Calendario() {
        this.eventos = new ArrayList<Evento>();
        this.tareas = new ArrayList<Tarea>();
        this.alarmasProximas = new ArrayList<Alarma>();
    }

    /* public void crearEvento(LocalDateTime fechaInicio, LocalDateTime fechaFinal, int cantidadRepeticiones, TipoRepeticion tipoRepe){
       // Evento nuevoEvento = new (fechaInicio, fechaFinal, tipoRepe, cantidadRepeticiones);

    }
    */
}
