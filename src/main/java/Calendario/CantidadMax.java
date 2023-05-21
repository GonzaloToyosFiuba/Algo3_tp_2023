package Calendario;

import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CantidadMax extends Evento{
    @JsonProperty("repeticionesMax")
    int repeticionesMax;
    public CantidadMax(UUID id, String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, TipoFrecuencia tipoFrecuencia, int repeticionesMax, boolean diaCompleto) {
        super(id, descripcion, titulo, fechaInicio, fechaFinal, tipoFrecuencia, diaCompleto);
        this.repeticionesMax = repeticionesMax;
    }

    @JsonCreator
    private  CantidadMax (@JsonProperty("id") UUID id,
                          @JsonProperty("descripcion") String descripcion,
                          @JsonProperty("titulo") String titulo,
                          @JsonProperty("fechaInicio") LocalDateTime fechaInicio,
                          @JsonProperty("fechaFinal") LocalDateTime fechaFinal,
                          @JsonProperty("tipoFrecuencia") TipoFrecuencia tipoFrecuencia,
                          @JsonProperty("diaCompleto") boolean diaCompleto,
                          @JsonProperty("alarmas") ArrayList<Alarma> alarmas,
                          @JsonProperty("repeticionesMax") int repeticionesMax) {
        super(id, descripcion, titulo, fechaInicio, fechaFinal,tipoFrecuencia, diaCompleto);
        super.alarmas = alarmas;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CantidadMax evento = (CantidadMax) o;
        return diaCompleto == evento.diaCompleto &&
                contadorIdAlarmas == evento.contadorIdAlarmas &&
                Objects.equals(id, evento.id) &&
                Objects.equals(descripcion, evento.descripcion) &&
                Objects.equals(titulo, evento.titulo) &&
                Objects.equals(fechaInicio, evento.fechaInicio) &&
                Objects.equals(fechaFinal, evento.fechaFinal) &&
                Objects.equals(tipoFrecuencia, evento.tipoFrecuencia) &&
                alarmas.containsAll(evento.alarmas) &&
                repeticionesMax == evento.repeticionesMax;
    }
}
