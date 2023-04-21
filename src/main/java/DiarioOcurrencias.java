import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiarioOcurrencias extends Repeticion{
    private int intervalo, repeticionesMax;

    public DiarioOcurrencias(int intervalo, int repeticionesMax) {
        this.intervalo = intervalo;
        this.repeticionesMax = repeticionesMax;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;
        //LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        for (int i = 0; i < repeticionesMax ; i++){ // -> FALTA TEST !!!!!!!!!e

            if (i > 0){
                aux_fInicial = fechaFinal.plusDays(intervalo * i).withHour(fechaInicio.getHour()).withMinute(fechaInicio.getMinute());
                //aux_fFinal = aux_fInicial.plus(duracion);
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 ){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }
}
