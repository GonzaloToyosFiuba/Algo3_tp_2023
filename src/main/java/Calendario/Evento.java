package Calendario;

import CustomDeserializers.LocalDateTimeDeserializer;
import CustomSerializers.LocalDateTimeSerializer;
import Frecuencias.Anual;
import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoEvento"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CantidadMax.class, name = "cantidadMax"),
        @JsonSubTypes.Type(value = FechaLimite.class, name = "fechaLimite"),
})
public abstract class Evento implements Agendable {
    @JsonProperty("id")
    protected UUID id;
    @JsonProperty("descripcion")
    protected String descripcion;
    @JsonProperty("titulo")
    protected String titulo;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime fechaInicio;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime fechaFinal;
    @JsonProperty("alarmas")
    protected ArrayList<Alarma> alarmas;
    @JsonProperty("tipoFrecuencia")
    protected TipoFrecuencia tipoFrecuencia;
    @JsonProperty("diaCompleto")
    protected boolean diaCompleto;
    @JsonProperty("contadorIdAlarmas")
    protected int contadorIdAlarmas;

    public Evento(UUID id, String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, TipoFrecuencia tipoFrecuencia,boolean diaCompleto) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
        this.contadorIdAlarmas = 0;
        this.alarmas = new ArrayList<>();
        this.tipoFrecuencia = tipoFrecuencia;
        this.diaCompleto = diaCompleto;
    }
    public ArrayList<Alarma> obtenerAlarmasOrdenadas(){
        return alarmas.stream()
                .sorted(Comparator.comparing(Alarma::getHorarioFechaDisparo))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @JsonIgnore
    public UUID getId(){
        return this.id;
    }
    @JsonIgnore
    public TipoFrecuencia getTipoFrecuencia(){
        return tipoFrecuencia;
    }
    @JsonIgnore
    public LocalDateTime getFechaInicio(){
        return this.fechaInicio;
    }
    @JsonIgnore
    public String getTitulo(){
        return this.titulo;
    }
    @JsonIgnore
    public String getDescripcion(){
        return this.descripcion;
    }
    @JsonIgnore
    public Duration duracionEvento(){
        return Duration.between(fechaInicio,fechaFinal);
    }
    public void agregarAlarmaRepetible(int minutosAntes, TipoAlarma tipo){
        // crea una alarma mandando la fecha original del eveto menos los minutos antes y con el tipo , en la parte repetible es true
        LocalDateTime fechaDisparo = fechaInicio.minusMinutes(minutosAntes);
        Alarma nuevaAlarma = new Alarma(fechaDisparo, tipo, true, this.contadorIdAlarmas);
        alarmas.add(nuevaAlarma);
        this.contadorIdAlarmas++;
    }

    public void agregarAlarmaUnica(LocalDateTime fechaDisparo, TipoAlarma tipo){
        // crea una alarma que la hora de disparado dada y si tipo, en la parte repeticion es False.
        Alarma nuevaAlarma = new Alarma(fechaDisparo, tipo, this.contadorIdAlarmas);
        alarmas.add(nuevaAlarma);
        this.contadorIdAlarmas++;
    }

    public boolean esDiaCompleto(){
        return diaCompleto;
    }

    public final ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual){
        ArrayList<Alarma> alarmasAux = new ArrayList<>();

        for (Alarma alarma:this.alarmas) {
            if (alarma.esRepetible()){
                this.procesarAlarmaRepetible(alarma.getHorarioFechaDisparo(), horarioActual, alarmasAux, alarma);
            } else if (horarioActual.compareTo(alarma.getHorarioFechaDisparo()) <= 0){
                Alarma alarmaEnvio = new Alarma(alarma.getHorarioFechaDisparo(), alarma.getTipo(), false, alarma.getId());
                alarmasAux.add(alarmaEnvio);
            }
        }

        ArrayList<Alarma> alarmasRetorno = new ArrayList<>();

        if(!alarmasAux.isEmpty()){
            Alarma alarmaMinima = Collections.min(alarmasAux);
            for (Alarma a : alarmasAux) {
                if(a.compareTo(alarmaMinima) == 0){
                    a.setMensaje(this.descripcion);
                    alarmasRetorno.add(a);
                }
            }
        }

        return alarmasRetorno;
    }

    protected abstract void procesarAlarmaRepetible(LocalDateTime aux_fAlarma, LocalDateTime horarioActual, ArrayList<Alarma> alarmasAux, Alarma alarma);

    public abstract ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2);
    public void eliminarAlarma(int id){
        int posicion = 0;

        for(Alarma alarma:this.alarmas){
            if (alarma.getId() == id){
                alarmas.remove(posicion);
                break;
            }
            posicion++;
        }
    }

    public void editarEvento(String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        if (fechaInicio != this.fechaInicio){ // Esto es momentaneo , hay que hacer que las alarmas se pueda reconfigurar respecto a un cambio de fecha
            alarmas.clear();
            this.contadorIdAlarmas = 0;
        }
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
    }

    @JsonProperty("tipoAgendable")
    private String getTipoAgendable() {
        return Evento.class.getSimpleName();
    }

    @Override
    public void aceptar(VisitorAgendable visitor) {
        visitor.visitarEvento(this);
    }

}
