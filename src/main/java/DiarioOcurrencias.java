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
        LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);// fini 12 ffinal 14 -> 17

        for (int i = 0; i < repeticionesMax ; i++){ // -> FALTA TEST !!!!!!!!!e

            if (i > 0){
                aux_fInicial = aux_fFinal.plusDays(intervalo).withHour(fechaInicio.getHour()).withMinute(fechaInicio.getMinute());
                aux_fFinal = aux_fInicial.plus(duracion);
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 ){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicial, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        ArrayList<Alarma> alarmasAux = new ArrayList<Alarma>();
        LocalDateTime aux_fAlarma;
        LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion;

        for(Alarma alarma:alarmas){
            aux_fAlarma = alarma.getHorarioFechaDisparo();
            duracion = Duration.between( alarma.getHorarioFechaDisparo(), fechaFinal);
            for (int i = 0; i < repeticionesMax ; i++){ // -> FALTA TEST !!!!!!!!!e

                if ( i > 0){
                    aux_fAlarma = aux_fFinal.plusDays(intervalo).withHour(alarma.getHorarioFechaDisparo().getHour()).withMinute(alarma.getHorarioFechaDisparo().getMinute());
                    aux_fFinal = aux_fAlarma.plus(duracion);
                }

                if (horarioActual.compareTo(aux_fAlarma) <= 0){
                    alarmasAux.add(alarma);
                    break;
                }

            }
        }
        return alarmasAux;
    }
    public static void main(String[] args) {
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 19, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 12, 10, 18, 55);


        Repeticion repe = new DiarioOcurrencias(2, 10);

        Evento e = new Ocurrencias(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);


        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        for (LocalDateTime fecha : fechas) {
            System.out.println(fecha);
        }

        LocalDateTime f = LocalDateTime.MAX;
        System.out.println(f);

    }
}
