import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Repeticion {
    public abstract ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal);
    public abstract ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicio, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas);

}
