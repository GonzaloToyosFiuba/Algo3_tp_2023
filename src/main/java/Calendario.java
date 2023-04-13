import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario {
    private static enum  TipoRepeticion{
        NINGUNA,
        DIARIA,
        SEMANAL,
        MENSUAL,
        ANUAL,
    }
    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;
    private Alarma alarmaProxima;

    /* public void crearEvento(LocalDateTime fechaInicio, LocalDateTime fechaFinal, int cantidadRepeticiones, TipoRepeticion tipoRepe){
       // Evento nuevoEvento = new (fechaInicio, fechaFinal, tipoRepe, cantidadRepeticiones);

    }
    */
}
