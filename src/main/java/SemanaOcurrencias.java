import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class SemanaOcurrencias implements Repeticion {
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
        // NO ESTA TERMINADO NO SE PUEDE USAR
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        this.chequeoDiaSemana(fechaInicio);
        LocalDateTime aux_fInicial = fechaInicio.with(TemporalAdjusters.next(this.diasSemana.get(2)));
        DayOfWeek diaInicial = fechaInicio.getDayOfWeek();
        //dia.compareTo(diasSemana.get(0));
        // suponiendo que viene un arrayordenado como yo quiero V J S -> J V S
        // Esto deberia funcionar con un Array de DIAS ordenado: L M X J V
        // f1 > fInicial  -> f2
        // CUIDADO CON LAS CONDICIONES QUE GENERA f1
        int j = 0;

        for (int i = 0; i < repeticionesMax ; i++) { // -> FALTA TEST !!!!!!!!!e

            if (i > 0) {// 1 2  8     12
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
        return fechas;
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicial, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        return null;
    }


}
