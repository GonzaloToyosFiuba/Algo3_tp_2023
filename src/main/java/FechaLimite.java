import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FechaLimite extends Evento{

    private LocalDateTime fechaLimite;
    public FechaLimite(int id, String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, TipoFrecuencia tipoFrecuencia, LocalDateTime fechaLimite) {
        super(id, descripcion, titulo, fechaInicio, fechaFinal, tipoFrecuencia);
        this.fechaLimite = fechaLimite;
    }

    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2) {
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;

        for (int i = 0; (fechaLimite.compareTo(aux_fInicial) >= 0 && f2.compareTo(aux_fInicial) >= 0)  ; i++){
            if (i > 0) {
                aux_fInicial = tipoFrecuencia.obtenerProximoDia(aux_fInicial);
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 && fechaLimite.compareTo(aux_fInicial) >= 0) {
                fechas.add(aux_fInicial);
            }
        }

        return fechas;
    }
    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual) {
        return null;
    }
}
