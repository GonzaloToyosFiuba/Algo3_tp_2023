import java.time.LocalDateTime;
import java.util.ArrayList;

public class Unico extends Evento{
    public Unico(int cantidadRepeticiones, String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, LocalDateTime fechaLimite, int id) {
        super(cantidadRepeticiones, titulo, descripcion, fechaInicio, fechaFinal, fechaLimite, id);
    }

    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2) {
        return null;
    }

}
