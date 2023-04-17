import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Evento {
    protected int cantidadRepeticiones;
    private String titulo;
    private String descripcion;
    protected LocalDateTime fechaInicio;
    protected LocalDateTime fechaFinal;
    protected LocalDateTime fechaLimite;
    private int id;

    public Evento(int cantidadRepeticiones, String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, LocalDateTime fechaLimite, int id) {
        this.cantidadRepeticiones = cantidadRepeticiones;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaLimite = fechaLimite;
        this.id = id;
    }
    //*private lista o arbol (decidir luego ) Alarmas; -> lo mas probable es que sea con un arbol asi las alarmas estan
                                                        //ordenadas por fecha mas cercana

    public abstract ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2);

    //public abstract ArrayList<LocalDateTime> obtenerRepeticiones(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    /*public Alarma obtenerAlarmaProxima(LocalDateTime fechaActual){
    } */


}
/*
    calendario:
     Repeticion repe = new Semanal();
    Crear antes el tipo de repeticion en el calendario y mandarlo al evento.
 */