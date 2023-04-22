import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class SemanalFechaLimite extends Repeticion{
    private ArrayList<DayOfWeek> diasSemana;
    private LocalDateTime fechaLimite;

    public SemanalFechaLimite(ArrayList<DayOfWeek> diasSemana, LocalDateTime fechaLimite) {
        this.diasSemana = diasSemana;
        this.fechaLimite = fechaLimite;
    }

    private void chequeoDiaSemana(LocalDateTime fechaInicio){
        if (this.diasSemana.isEmpty()){
            this. diasSemana.add(fechaInicio.getDayOfWeek());
        }
    }
    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return null;
    }
}
