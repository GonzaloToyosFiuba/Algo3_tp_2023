import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

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

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicio, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        ArrayList<Alarma> alarmasAux = new ArrayList<>();
        LocalDateTime aux_fAlarma = fechaInicio;

        for (Alarma alarma:alarmas) {

            if (alarma.esRepetible()){
                aux_fAlarma = alarma.getHorarioFechaDisparo();

                for (int i = 0; i < repeticionesMax ; i++){

                    if (i > 0){
                        aux_fAlarma = fechaInicio.plusYears(intervalo * i).withHour(alarma.getHorarioFechaDisparo().getHour()).withMinute(alarma.getHorarioFechaDisparo().getMinute());
                    }

                    if (horarioActual.compareTo(aux_fAlarma) <= 0){
                        Alarma alarmaEnvio = new Alarma(aux_fAlarma, alarma.getTipo(), true, alarma.getId());
                        alarmasAux.add(alarmaEnvio);
                        break;
                    }

                }
            } else if (horarioActual.compareTo(alarma.getHorarioFechaDisparo()) <= 0){
                Alarma alarmaEnvio = new Alarma(alarma.getHorarioFechaDisparo(), alarma.getTipo(), false, alarma.getId());
                alarmasAux.add(alarmaEnvio);
            }

        }

        ArrayList<Alarma> alarmasRetorno = new ArrayList<>();

        if(!alarmasAux.isEmpty()){
            Alarma alarmaMinima = Collections.min(alarmasAux);
            for (Alarma a:alarmasAux) {
                if(a.compareTo(alarmaMinima) == 0){
                    alarmasRetorno.add(a);
                }
            }
        }

        return alarmasRetorno;
    }
}
