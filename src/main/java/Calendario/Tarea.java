package Calendario;

import CustomDeserializers.LocalDateTimeDeserializer;
import CustomSerializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarea implements Agendable{
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("completada")
    private boolean completada;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fechaVencimiento;
    @JsonProperty("alarmas")
    private ArrayList<Alarma> alarmas;
    @JsonProperty("diaCompleto")
    private boolean diaCompleto;
    @JsonProperty("contadorIdAlarmas")
    private int contadorIdAlarmas;

    public Tarea(UUID id, String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.diaCompleto = diaCompleto;
        this.alarmas = new ArrayList<>();
        this.contadorIdAlarmas = 0;
    }

    public List<Alarma> obtenerAlarmasOrnedas(){
        return alarmas.stream()
                .sorted(Comparator.comparing(Alarma::getHorarioFechaDisparo))
                .toList();
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    @JsonCreator
    private  Tarea (@JsonProperty("id") UUID id,
                    @JsonProperty("descripcion") String descripcion,
                    @JsonProperty("titulo") String titulo,
                    @JsonProperty("fechaVencimiento") LocalDateTime fechaVencimiento,
                    @JsonProperty("diaCompleto") boolean diaCompleto,
                    @JsonProperty("contadorIdAlarma") int contadorIdAlarmas,
                    @JsonProperty("alarmas") ArrayList<Alarma> alarmas) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.diaCompleto = diaCompleto;
        this.alarmas = alarmas;
        this.contadorIdAlarmas = contadorIdAlarmas;
    }

    public void completar(){
        this.completada = true;
    }

    @JsonIgnore
    public String getTitulo(){
        return this.titulo;
    }
    @JsonIgnore
    public String getDescripcion(){
        return this.descripcion;
    }

    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual){
        ArrayList<Alarma> alarmasAux = new ArrayList<>();

        for (Alarma alarma:this.alarmas) { // Esto para buscar solo alarmas que todavia no ocurrieron
            if(alarma.getHorarioFechaDisparo().compareTo(horarioActual) >= 0){
                alarmasAux.add(alarma);
            }
        }

        ArrayList<Alarma> alarmasRetorno = new ArrayList<>();

        if (!alarmasAux.isEmpty()){
            Alarma alarmaMinima = Collections.min(alarmasAux);

            for (Alarma alarma:alarmasAux) { // si hay alarmas repetidas
                if(alarma.compareTo(alarmaMinima) == 0){
                    alarmasRetorno.add(alarma);
                }
            }
        }

        return alarmasRetorno;
    }

    public void agregarAlarma(int minutosAntes, TipoAlarma tipo){
        LocalDateTime fechaDisparo = fechaVencimiento.minusMinutes(minutosAntes);
        Alarma alarma = new Alarma(fechaDisparo, tipo, this.contadorIdAlarmas);
        this.alarmas.add(alarma);
        this.contadorIdAlarmas++;
    }

    public void agregarAlarma(LocalDateTime fechaDisparo, TipoAlarma tipo){
        Alarma alarma = new Alarma(fechaDisparo, tipo, this.contadorIdAlarmas);
        this.alarmas.add(alarma);
        this.contadorIdAlarmas++;
    }

    public boolean esDiaCompleto(){
        return diaCompleto;
    }
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
    public void editarTarea(String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto){
        if (fechaVencimiento != this.fechaVencimiento){ // Esto es momentaneo , hay que hacer que las alarmas se pueda reconfigurar respecto a un cambio de fecha
            alarmas.clear();
            this.contadorIdAlarmas = 0;
        }
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.fechaVencimiento = fechaVencimiento;
        this.diaCompleto = diaCompleto;
    }
    public UUID getId(){
        return this.id;
    }

    @JsonProperty("tipoAgendable")
    private String getTipoAgendable() {
        return Tarea.class.getSimpleName();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return completada == tarea.completada &&
               diaCompleto == tarea.diaCompleto &&
               contadorIdAlarmas == tarea.contadorIdAlarmas &&
               Objects.equals(id, tarea.id) &&
               Objects.equals(titulo, tarea.titulo) &&
               Objects.equals(descripcion, tarea.descripcion) &&
               Objects.equals(fechaVencimiento, tarea.fechaVencimiento) &&
               alarmas.containsAll(tarea.alarmas);
    }

}
