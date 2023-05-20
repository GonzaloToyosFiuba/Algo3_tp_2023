import CustomSerializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Tarea {
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("completada")
    private boolean completada;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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
        this.alarmas = new ArrayList<Alarma>();
        this.contadorIdAlarmas = 0;
    }

    public void completar(){
        this.completada = true;
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
}
