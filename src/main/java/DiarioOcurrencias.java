import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class DiarioOcurrencias extends Repeticion{
    private int intervalo, repeticionesMax;

    public DiarioOcurrencias(int intervalo, int repeticionesMax) {
        this.intervalo = intervalo;
        this.repeticionesMax = repeticionesMax;
    }

    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        ArrayList<LocalDateTime> fechas = new ArrayList<>();
        LocalDateTime aux_fInicial = fechaInicio;
        LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        for (int i = 0; i < repeticionesMax ; i++){

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
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual, LocalDateTime fechaInicio, LocalDateTime fechaFinal, ArrayList<Alarma> alarmas) {
        ArrayList<Alarma> alarmasAux = new ArrayList<>();
        LocalDateTime aux_fAlarma;
        LocalDateTime aux_fFinal = fechaFinal;
        Duration duracion;

        for(Alarma alarma:alarmas){
            if (alarma.esRepetible()){
                aux_fAlarma = alarma.getHorarioFechaDisparo();
                duracion = Duration.between( alarma.getHorarioFechaDisparo(), fechaFinal);
                aux_fFinal = fechaFinal;
                for (int i = 0; i < repeticionesMax ; i++){

                    if (i > 0){
                        aux_fAlarma = aux_fFinal.plusDays(intervalo).withHour(alarma.getHorarioFechaDisparo().getHour()).withMinute(alarma.getHorarioFechaDisparo().getMinute());
                        aux_fFinal = aux_fAlarma.plus(duracion);
                    }

                    if (horarioActual.compareTo(aux_fAlarma) <= 0){
                        Alarma alarmaEnvio = new Alarma(aux_fAlarma, alarma.getTipo(),true, alarma.getId());
                        alarmasAux.add(alarmaEnvio);
                        break;
                    }

                }
            } else if (horarioActual.compareTo(alarma.getHorarioFechaDisparo()) <= 0){
                Alarma alarmaEnvio = new Alarma(alarma.getHorarioFechaDisparo(), alarma.getTipo(), false,alarma.getId());
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

    public static void main(String[] args) {
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 12, 10, 18, 55);


        Repeticion repe = new DiarioOcurrencias(2, 10);

        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);

        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);
        e.agregarAlarmaRepetible(36,TipoAlarma.CORREO);
        e.agregarAlarmaRepetible(45,TipoAlarma.NOTIFICACION);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 5, 20, 00),TipoAlarma.SONIDO);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);


        ArrayList<Alarma> alarmitas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Alarma a:alarmitas) {
            System.out.println(a.getHorarioFechaDisparo().format(formato));
        }

        Alarma alarmaMinima = Collections.min(alarmitas);
        System.out.println(alarmaMinima.getHorarioFechaDisparo().format(formato));

        System.out.println("\nFechas");

        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        for (LocalDateTime fecha : fechas) {
            System.out.println(fecha.format(formato));
        }

    }
}
