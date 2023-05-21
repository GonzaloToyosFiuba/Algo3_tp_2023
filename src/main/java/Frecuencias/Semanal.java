package Frecuencias;

import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.TreeSet;

public class Semanal implements TipoFrecuencia {
    @JsonProperty("intervalo")
    private int intervalo;

    @JsonProperty("dias")
    private DayOfWeek[] dias;

    public Semanal(int intervalo, TreeSet<DayOfWeek> dias) {
        this.dias = dias.toArray(new DayOfWeek[dias.size()]);
        this.intervalo = intervalo;
    }
    @JsonCreator
    private static Semanal create(@JsonProperty("intervalo") int intervalo, @JsonProperty("dias")DayOfWeek[] dias) {
        TreeSet<DayOfWeek> diasTree = new TreeSet<>();
        diasTree.addAll(Arrays.asList(dias));
        return new Semanal(intervalo,diasTree);
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
