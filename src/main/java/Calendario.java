import java.security.spec.ECField;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Calendario {
    private HashMap<Integer,Evento> eventos;
    private HashMap<Integer,Tarea> tareas;
    private ArrayList<Alarma> alarmasProximas;

    public Calendario() {
        this.eventos = new HashMap<>();
        this.tareas = new HashMap<>();
        this.alarmasProximas = new ArrayList<>();
    }

    public void agregarEvento(int id, String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, boolean diaCompleto){
        if(diaCompleto){
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }
        Evento nuevoEvento = new Evento(id, descripcion, titulo , tipoRepeticion, fechaInicio, fechaFinal);
        eventos.put(id,nuevoEvento);
    }

    public  void agregarTarea(int id, String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto){
        Tarea nuevaTarea = new Tarea(id, titulo, descripcion, fechaVencimiento, diaCompleto);
        tareas.put(id,nuevaTarea);
    }

    public Evento buscarEvento(int id){
        return this.eventos.get(id);
    }

    public  Tarea buscarTarea(int id){
        return this.tareas.get(id);
    }

    public void eliminarEvento(int id){
        this.eventos.remove(id);
    }
    public void eliminarTarea(int id){
        this.tareas.remove(id);
    }
    public void EditarEvento(int id,String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, boolean diaCompleto){
        if(diaCompleto){
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }
        this.eventos.get(id).editarEvento(descripcion, titulo, tipoRepeticion, fechaInicio, fechaFinal);
    }
    public void EditarTarea(int id,String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto){
        this.tareas.get(id).editarTarea(titulo,descripcion, fechaVencimiento, diaCompleto);
    }
}
