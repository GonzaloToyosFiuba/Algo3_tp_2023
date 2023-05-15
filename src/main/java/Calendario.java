import java.security.spec.ECField;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


public class Calendario {
    private HashMap<UUID,Evento> eventos;
    private HashMap<UUID,Tarea> tareas;
    private Set<UUID> ids;
    //private ArrayList<Alarma> alarmasProximas;

    public Calendario() {
        this.eventos = new HashMap<>();
        this.tareas = new HashMap<>();
        //this.alarmasProximas = new ArrayList<>();
    }
    private UUID generarIdUnica(){
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (ids.contains(id));

        ids.add(id);
        return  id;
    }
    public void agregarEventoCantMax( String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal,int repeticionesMax, TipoFrecuencia tipoFrecuencia, boolean diaCompleto){
        /*if(diaCompleto){
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }*/
        UUID id = generarIdUnica();
        Evento nuevoEvento = new CantidadMax(id, descripcion, titulo , fechaInicio, fechaFinal, tipoFrecuencia, repeticionesMax, diaCompleto);
        eventos.put(id, nuevoEvento);
    }
    public void agregarEventoFechaLimite( String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal,LocalDateTime fechaLimite, TipoFrecuencia tipoFrecuencia, boolean diaCompleto){
        /*if(diaCompleto){
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }*/
        UUID id = generarIdUnica();
        Evento nuevoEvento = new FechaLimite(id, descripcion, titulo ,fechaInicio, fechaFinal, tipoFrecuencia, fechaLimite, diaCompleto);
        eventos.put(id, nuevoEvento);
    }

    public  void agregarTarea( String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto){
        UUID id = generarIdUnica();
        Tarea nuevaTarea = new Tarea(id, titulo, descripcion, fechaVencimiento, diaCompleto);
        tareas.put(id, nuevaTarea);
    }

    public Evento buscarEvento(UUID id){
        return this.eventos.get(id);
    }

    public  Tarea buscarTarea(UUID id){
        return this.tareas.get(id);
    }

    public void eliminarEvento(UUID id){
        this.eventos.remove(id);
        ids.remove(id);
    }
    public void eliminarTarea(UUID id){
        this.tareas.remove(id);
    }
    public void EditarEvento(UUID id,String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, boolean diaCompleto){
        if(diaCompleto){
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }
        this.eventos.get(id).editarEvento(descripcion, titulo, fechaInicio, fechaFinal);
    }
    public void EditarTarea(UUID id,String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto){
        this.tareas.get(id).editarTarea(titulo,descripcion, fechaVencimiento, diaCompleto);
    }
}
