import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiarioFechaLimite implements Repeticion{
    private int intervalo;
    private LocalDateTime fechaLimite;

    public DiarioFechaLimite(int intervalo, LocalDateTime fechaLimite) {
        this.intervalo = intervalo;
        this.fechaLimite = fechaLimite;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){

        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;
        LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        for (int i = 0; (fechaLimite.compareTo(aux_fInicial) >= 0 && f2.compareTo(aux_fInicial) >= 0)  ; i++){

            if (i > 0){
                aux_fInicial = aux_fFinal.plusDays(intervalo).withHour(fechaInicio.getHour()).withMinute(fechaInicio.getMinute());
                aux_fFinal = aux_fInicial.plus(duracion);
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 && fechaLimite.compareTo(aux_fInicial) >= 0){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicial, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        return null;
    }
}
