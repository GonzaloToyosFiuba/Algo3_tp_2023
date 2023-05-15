import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.time.format.DateTimeFormatter;

public class Semanal implements TipoFrecuencia {
    private int intervalo;
    private DayOfWeek[] dias;

    public Semanal(int intervalo, TreeSet<DayOfWeek> dias) {
        DayOfWeek[] diasArray = dias.toArray(new DayOfWeek[dias.size()]);
        this.dias = diasArray;
        this.intervalo = intervalo;
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        DayOfWeek diaActual = dia.getDayOfWeek();
        int i = 0;

        while (diaActual.compareTo(dias[i]) != 0 && i < dias.length){//hay que buscar en que posicion esta el dia que se mando
            i++;
        }

        LocalDateTime diaRetorno;

        if(i == dias.length-1){//si mando el ultimo de los dias de repeticion se da el primero y se pasa a la semana segun el intervalo
            diaRetorno = dia.with( TemporalAdjusters.next(dias[0]) ).plusWeeks(intervalo - 1);
        } else {//si no se da el siguiente normal de la misma semana
            diaRetorno = dia.with( TemporalAdjusters.next(dias[i + 1]) );
        }

        return diaRetorno;
    }
}
