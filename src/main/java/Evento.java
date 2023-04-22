import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract  class Evento {
    protected int id;
    protected String descripcion;
    protected String titulo;
    protected Repeticion tipoRepeticion;

    protected LocalDateTime fechaInicio;

    protected LocalDateTime fechaFinal;
    protected ArrayList<Alarma> alarmas;

    public Evento(int id, String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.tipoRepeticion = tipoRepeticion;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
    }

    public void  agregarAlarmaRepetible(int minutosAntes, TipoAlarma tipo){
        // crea una alarma mandando la fecha original del eveto menos los minutos antes y con el tipo , en la parte repetible es true
    }

    public void agregarAlarmaUnica(LocalDateTime horaDisparo,TipoAlarma tipo){
        // crea una alarma que la hora de disparado dada y si tipo, en la parte repeticion es False.
    }

    public abstract ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual);

    public abstract ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2);
}
