package Calendario;

import CustomDeserializers.LocalDateTimeDeserializer;
import CustomSerializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Alarma implements Comparable{
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime horarioFechaDisparo;
    @JsonProperty("tipoAlarma")
    private TipoAlarma tipoAlarma;
    @JsonProperty("id")
    private int id;
    @JsonProperty("repetible")
    private boolean repetible;
    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo, boolean repetible, int id) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
        this.repetible = repetible;
        this.id = id;
    }

    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo, int id) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
        this.id = id;
        this.repetible = false;
    }

    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
    }

    @JsonCreator
    private static Alarma create(@JsonProperty("horarioFechaDisparo") LocalDateTime horarioFechaDisparo,
                                 @JsonProperty("tipo")TipoAlarma tipo,
                                 @JsonProperty("repetible")boolean repetible,
                                 @JsonProperty("id")int id) {
        return new Alarma(horarioFechaDisparo, tipo, repetible, id);
    }

    public String disparar(LocalDateTime fechaActual){
        String seDispara = "No se disparo";
        if (this.horarioFechaDisparo.compareTo(fechaActual.truncatedTo(ChronoUnit.MINUTES)) == 0){
            seDispara = tipoAlarma.dipararAlarma();
        }
        return seDispara;
    }

    public LocalDateTime getHorarioFechaDisparo() {
        return horarioFechaDisparo;
    }

    @JsonIgnore
    public TipoAlarma getTipo() {
        return tipoAlarma;
    }

    public boolean esRepetible(){
        return this.repetible;
    }

    public void setTipo(TipoAlarma tipo) {
        this.tipoAlarma = tipo;
    }
    public boolean setAlarma(LocalDateTime disparo){
        this.horarioFechaDisparo =  disparo;
        return true;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public int compareTo(Object o) {
        return this.horarioFechaDisparo.compareTo( ((Alarma)o).getHorarioFechaDisparo() );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarma alarma = (Alarma) o;
        return Objects.equals(horarioFechaDisparo, alarma.horarioFechaDisparo) && tipoAlarma == alarma.tipoAlarma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(horarioFechaDisparo, tipoAlarma, id, repetible);
    }

}
