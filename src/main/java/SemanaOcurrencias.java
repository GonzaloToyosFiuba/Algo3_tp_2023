import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class SemanaOcurrencias extends Repeticion {
    private int  repeticionesMax;
    private ArrayList<DayOfWeek> diasSemana;

    public SemanaOcurrencias(int repeticionesMax, ArrayList<DayOfWeek> diasSemana) {
        this.repeticionesMax = repeticionesMax;
        this.diasSemana = diasSemana;
    }


    private void chequeoDiaSemana(LocalDateTime fechaInicio){
        if (this.diasSemana.isEmpty()){
            this. diasSemana.add(fechaInicio.getDayOfWeek());
        }
    }
    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        this.chequeoDiaSemana(fechaInicio);
        LocalDateTime aux_fInicial = fechaInicio.with(TemporalAdjusters.next(this.diasSemana.get(0)));
        DayOfWeek dia = fechaInicio.getDayOfWeek();
        dia.compareTo(diasSemana.get(0));
        int j = 0;

        for (int i = 0; i < repeticionesMax ; i++) { // -> FALTA TEST !!!!!!!!!e

            if (i > 0) {
                aux_fInicial = aux_fInicial.with(TemporalAdjusters.next(this.diasSemana.get(j))); //una vez que j llega al maximo tiene que volver al j=0 los dias tienen que estar ordenado
                j++;
                if (j >= diasSemana.size()) {
                    j = 0;
                }
            }
            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 ){
                fechas.add(aux_fInicial);
            }
        }


        /*LocalDateTime aux_fInicial = fechaInicio;
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
        */
        return fechas;
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicial, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        return null;
    }
}
