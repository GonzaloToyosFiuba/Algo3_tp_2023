import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario {
    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;
    private ArrayList<Alarma> alarmasProximas;

    public Calendario() {
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
        this.alarmasProximas = new ArrayList<>();
    }

    public void agregarEvento(int id, String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, boolean diaCompleto){
        if(diaCompleto){
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }

        Evento nuevoEvento = new Evento(id, descripcion, titulo , tipoRepeticion, fechaInicio, fechaFinal);
        eventos.add(nuevoEvento);
    }

    public  void agregarTarea(int id, String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto){
        Tarea nuevaTarea = new Tarea(id, titulo, descripcion, fechaVencimiento, diaCompleto);
        tareas.add(nuevaTarea);
    }

    public Evento buscarEvento(int id){
        Evento eventoBuscado = null;

        for(Evento evento:eventos){
            if ( id == evento.getId()){
                eventoBuscado = evento;
                break;
            }
        }
        return eventoBuscado;
    }

    public  Tarea buscarTarea(int id){
        Tarea tareaBuscada = null;

        for (Tarea tarea:tareas){
            if (id == tarea.getId()){
                tareaBuscada = tarea;
            }
        }
        return tareaBuscada;
    }
}
