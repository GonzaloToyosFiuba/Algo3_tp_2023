import java.time.LocalDateTime;
import java.util.ArrayList;

public class FechaLimite extends Evento{


    public FechaLimite(int id, String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, TipoFrecuencia tipoFrecuencia) {
        super(id, descripcion, titulo, fechaInicio, fechaFinal, tipoFrecuencia);
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual) {
        return null;
    }

    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2) {
        return null;
    }
}
