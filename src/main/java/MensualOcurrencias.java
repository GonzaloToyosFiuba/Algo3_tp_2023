import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MensualOcurrencias extends Repeticion{
    private int intervalo, repeticionesMax;

    public MensualOcurrencias(int intervalo, int repeticionesMax) {
        this.intervalo = intervalo;
        this.repeticionesMax = repeticionesMax;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;

        for (int i = 0; i < repeticionesMax ; i++){
            if (i > 0){
                aux_fInicial = fechaInicio.plusMonths(intervalo * i).withHour(fechaInicio.getHour()).withMinute(fechaInicio.getMinute());
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 ){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }

    /*public static void main(String[] args) {
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 5, 5, 56);

        LocalDateTime f1 = LocalDateTime.of(2022, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2022, 12, 10, 18, 55);


        Repeticion repe = new MensualOcurrencias(2, 10);

        Evento e = new Ocurrencias(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);


        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        for (LocalDateTime fecha : fechas) {
            System.out.println(fecha);
        }

        LocalDateTime f = LocalDateTime.MAX;
        System.out.println(f);

    }*/
}