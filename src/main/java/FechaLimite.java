import java.time.LocalDateTime;
import java.util.ArrayList;

public class FechaLimite extends Evento{
    public FechaLimite(int id, String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        super(id, descripcion, titulo, tipoRepeticion, fechaInicio, fechaFinal);
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2){
        return tipoRepeticion.obtenerRepeticionesEntre(f1, f2, fechaInicio, fechaFinal);
    }
}
