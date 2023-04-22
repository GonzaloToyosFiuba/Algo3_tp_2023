import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiarioFechaLimite extends Repeticion{
    private int intervalo;
    private LocalDateTime fechaLimite;

    public DiarioFechaLimite(int intervalo, LocalDateTime fechaLimite) {
        this.intervalo = intervalo;
        this.fechaLimite = fechaLimite;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){

        return null;
    }
}
