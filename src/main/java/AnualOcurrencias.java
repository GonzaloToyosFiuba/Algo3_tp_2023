import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AnualOcurrencias extends Repeticion{
    private int intervalo, repeticionesMax;

    public AnualOcurrencias(int intervalo, int repeticionesMax) {
        this.intervalo = intervalo;
        this.repeticionesMax = repeticionesMax;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        for (int i = 0; i < repeticionesMax ; i++){

            if (i > 0){
                aux_fInicial = fechaInicio.plusYears(intervalo * i).withHour(fechaInicio.getHour()).withMinute(fechaInicio.getMinute());
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 ){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }
}
