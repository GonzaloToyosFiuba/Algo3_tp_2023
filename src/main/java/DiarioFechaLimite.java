import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DiarioFechaLimite extends Repeticion{
    private int intervalo;
    private LocalDateTime fechaLimite;

    public DiarioFechaLimite(int intervalo, LocalDateTime fechaLimite) {
        this.intervalo = intervalo;
        this.fechaLimite = fechaLimite;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){

        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;
        LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        for (int i = 0; (fechaLimite.compareTo(aux_fInicial) >= 0 && f2.compareTo(aux_fInicial) >= 0)  ; i++){

            if (i > 0){
                aux_fInicial = aux_fFinal.plusDays(intervalo).withHour(fechaInicio.getHour()).withMinute(fechaInicio.getMinute());
                aux_fFinal = aux_fInicial.plus(duracion);
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 && fechaLimite.compareTo(aux_fInicial) >= 0){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicial, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        return null;
    }

    /*public static void main(String[] args) {
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 19, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 10, 25, 18, 56);


        Repeticion repe = new DiarioFechaLimite(2, LocalDateTime.MAX);

        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);


        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        for (LocalDateTime fecha : fechas) {
            System.out.println(fecha);
        }

        LocalDateTime f = LocalDateTime.MAX;
        System.out.println(f);

    }*/
}
