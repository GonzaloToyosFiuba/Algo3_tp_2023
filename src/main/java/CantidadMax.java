import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class CantidadMax extends Evento{

    int repeticionesMax;
    public CantidadMax(int id, String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, TipoFrecuencia tipoFrecuencia, int repeticionesMax) {
        super(id, descripcion, titulo, fechaInicio, fechaFinal, tipoFrecuencia);
        this.repeticionesMax = repeticionesMax;
    }

    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2) {
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = fechaInicio;

        for (int i = 0; i < repeticionesMax ; i++){
            if (i > 0){
                aux_fInicial = tipoFrecuencia.obtenerProximoDia(aux_fInicial);
            }

            if (f1.compareTo(aux_fInicial) <= 0 && f2.compareTo(aux_fInicial) >= 0 ){
                fechas.add(aux_fInicial);
            }
        }
        return fechas;
    }

    @Override
    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual){
        ArrayList<Alarma> alarmasAux = new ArrayList<>();
        LocalDateTime aux_fAlarma = fechaInicio;

        for (Alarma alarma:this.alarmas) {

            if (alarma.esRepetible()){
                aux_fAlarma = alarma.getHorarioFechaDisparo();

                for (int i = 0; i < repeticionesMax ; i++){

                    if (i > 0){
                        aux_fAlarma = tipoFrecuencia.obtenerProximoDia(aux_fAlarma);
                        //aux_fAlarma = fechaInicio.plusMonths(intervalo * i).withHour(alarma.getHorarioFechaDisparo().getHour()).withMinute(alarma.getHorarioFechaDisparo().getMinute());
                    }

                    if (horarioActual.compareTo(aux_fAlarma) <= 0){
                        Alarma alarmaEnvio = new Alarma(aux_fAlarma, alarma.getTipo(), true, alarma.getId()); // VER COMO HACER EN PROTOTYPE
                        alarmasAux.add(alarmaEnvio);
                        break;
                    }

                }
            } else if (horarioActual.compareTo(alarma.getHorarioFechaDisparo()) <= 0){
                Alarma alarmaEnvio = new Alarma(alarma.getHorarioFechaDisparo(), alarma.getTipo(), false, alarma.getId());
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
        TipoFrecuencia tipoFrecuencia = new Diaria(2);
        Evento evento = new CantidadMax(8,"PASEAR","NADA",LocalDateTime.of(2023, 5, 1, 00, 10),LocalDateTime.of(2023, 5, 1, 19, 56),tipoFrecuencia,5);
        ArrayList<LocalDateTime> fechas = evento.obtenerRepeticionesEntre(LocalDateTime.of(2023, 4, 1, 18, 56),LocalDateTime.of(2023, 5, 12, 18, 56));

        for (var fechitas:fechas){
            System.out.println(fechitas);
        }
        evento.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);
        ArrayList<Alarma> alarmita = evento.obtenerProximaAlarma(LocalDateTime.of(2023, 4, 1, 15, 56));
        System.out.println("ALARMA");
        for (var alarma:alarmita){
            System.out.println(alarma.getHorarioFechaDisparo());
        }
    }
}
