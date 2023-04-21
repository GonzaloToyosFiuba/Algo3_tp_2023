import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract  class Evento {
    protected int id;
    protected String descripcion;
    protected String titulo;
    protected Repeticion tipoRepeticion;
    protected LocalDateTime fechaInicio;
    protected LocalDateTime fechaFinal;

    public Evento(int id, String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.tipoRepeticion = tipoRepeticion;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
    }

    public abstract ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2);
}
