import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;

public class Semanal implements TipoFrecuencia {
    private int intervalo;
    private DayOfWeek[] dias;

    public Semanal(int intervalo, DayOfWeek[] dias) {
        Arrays.sort(dias);
        this.dias = dias;
        this.intervalo = intervalo;
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        DayOfWeek diaActual = dia.getDayOfWeek();
        int i = 0;

        while (diaActual.compareTo(dias[i]) != 0 && i < dias.length){
            i++;
        }

        DayOfWeek diaDeLaSemanaSiguiente;

        if(i == dias.length-1){
            diaDeLaSemanaSiguiente = dias[dias.length];
        } else {
            diaDeLaSemanaSiguiente = dias[i + 1];
        }

        return dia.with(TemporalAdjusters.next(diaDeLaSemanaSiguiente));
    }

    public static void main(String[] args) {
        DayOfWeek[] days = {DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY, DayOfWeek.THURSDAY};
        Semanal s = new Semanal(4, days);

        System.out.println( s.obtenerProximoDia(LocalDateTime.of(2023, 5, 1, 00, 10)) );

        System.out.println( s.obtenerProximoDia(LocalDateTime.of(2023, 5, 4, 00, 10)) );
    }
}
