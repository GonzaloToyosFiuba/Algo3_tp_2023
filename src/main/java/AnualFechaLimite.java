import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AnualFechaLimite extends Repeticion{
    private int intervalo;
    private LocalDateTime fechaLimite;

    public AnualFechaLimite(int intervalo, LocalDateTime fechaLimite) {
        this.intervalo = intervalo;
        this.fechaLimite = fechaLimite;
    }
    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return null;
    }
}
