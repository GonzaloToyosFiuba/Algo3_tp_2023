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
        Arrays.sort(diasArray);
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

    public static void main(String[] args) {
       /* TreeSet<DayOfWeek> diasArbol = new TreeSet<>();

        diasArbol.add(DayOfWeek.THURSDAY);
        diasArbol.add(DayOfWeek.MONDAY);
        diasArbol.add(DayOfWeek.SUNDAY);
        diasArbol.add(DayOfWeek.FRIDAY);

        Semanal s = new Semanal(2, diasArbol);

        LocalDateTime diaI = LocalDateTime.of(2023, 5, 1, 00, 10);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE/dd/MMMM HH:mm");
        for (int i = 0; i < 20; i++){
            System.out.println( diaI.format(formatter) );
            diaI = s.obtenerProximoDia(diaI);
        }*/
        TipoFrecuencia tipoFrecuencia = new Diaria(10);
        Evento evento = new FechaLimite(8, "PASEAR", "NADA", LocalDateTime.of(2023, 5, 1, 00, 10), LocalDateTime.of(2023, 5, 1, 19, 56), tipoFrecuencia, LocalDateTime.of(2023, 5, 2, 19, 56));
        ArrayList<LocalDateTime> fechas = evento.obtenerRepeticionesEntre(LocalDateTime.of(2023, 4, 1, 18, 56), LocalDateTime.of(2023, 5, 12, 18, 56));

        for (var fechitas : fechas) {
            System.out.println(fechitas);
        }
        evento.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);
        ArrayList<Alarma> alarmita = evento.obtenerProximaAlarma(LocalDateTime.of(2023, 5, 30, 23, 40));
        System.out.println("ALARMA");
        for (var alarma : alarmita) {
            System.out.println(alarma.getHorarioFechaDisparo());
        }
    }
}
