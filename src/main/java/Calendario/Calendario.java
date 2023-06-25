package Calendario;

import CustomDeserializers.HashMapDeserializer;
import CustomSerializers.HashMapSerializer;
import Frecuencias.Diaria;
import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;



public class Calendario {
    @JsonSerialize(using = HashMapSerializer.class)
    @JsonDeserialize(using = HashMapDeserializer.class)
    private HashMap<UUID, Evento> eventos;
    @JsonSerialize(using = HashMapSerializer.class)
    @JsonDeserialize(using = HashMapDeserializer.class)
    private HashMap<UUID, Tarea> tareas;

    public Calendario() {
        this.eventos = new HashMap<>();
        this.tareas = new HashMap<>();
    }

    @JsonCreator
    private Calendario(@JsonProperty("eventos") HashMap<UUID, Evento> eventos,
                       @JsonProperty("tareas") HashMap<UUID, Tarea> tareas) {
        this.eventos = eventos;
        this.tareas = tareas;
    }

    private UUID generarIdUnica() {
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (tareas.containsKey(id) || eventos.containsKey(id));

        return id;
    }

    public void agregarEventoCantMax(String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, int repeticionesMax, TipoFrecuencia tipoFrecuencia, boolean diaCompleto, ArrayList<Alarma> alarmas) {
        UUID id = generarIdUnica();
        Evento nuevoEvento = new CantidadMax(id, descripcion, titulo, fechaInicio, fechaFinal, tipoFrecuencia, repeticionesMax, diaCompleto);
       agregarAlarmas(nuevoEvento,alarmas,fechaInicio);
        eventos.put(id, nuevoEvento);
    }

    public void agregarEventoFechaLimite(String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, LocalDateTime fechaLimite, TipoFrecuencia tipoFrecuencia, boolean diaCompleto, ArrayList<Alarma> alarmas) {
        UUID id = generarIdUnica();
        Evento nuevoEvento = new FechaLimite(id, descripcion, titulo, fechaInicio, fechaFinal, tipoFrecuencia, fechaLimite, diaCompleto);
        agregarAlarmas(nuevoEvento,alarmas,fechaInicio);
        eventos.put(id, nuevoEvento);
    }

    private void agregarAlarmas(Evento nuevoEvento,ArrayList<Alarma> alarmas,LocalDateTime fechaInicio){
        for (Alarma alarma : alarmas){
            if (alarma.esRepetible()){
                Duration duracion = Duration.between(alarma.getHorarioFechaDisparo(),fechaInicio);
                nuevoEvento.agregarAlarmaRepetible((int)duracion.toMinutes(),alarma.getTipo());
            }else {
                nuevoEvento.agregarAlarmaUnica(alarma.getHorarioFechaDisparo(),alarma.getTipo());
            }
        }
    }

    public void agregarTarea(String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto, ArrayList<Alarma> alarmas) {
        UUID id = generarIdUnica();
        Tarea nuevaTarea = new Tarea(id, titulo, descripcion, fechaVencimiento, diaCompleto);
        for (Alarma alarma : alarmas){
            nuevaTarea.agregarAlarma(alarma.getHorarioFechaDisparo(), alarma.getTipo());
        }
        tareas.put(id, nuevaTarea);
    }

    public ArrayList<RepresentacionAgendable> obtenerAgendables(LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        ArrayList<RepresentacionAgendable> listaAgendables = new ArrayList<>();

        eventos.forEach( (key, value) -> value.obtenerRepeticionesEntre(fechaInicio, fechaFinal)
                                              .forEach(fecha -> listaAgendables.add(new RepresentacionAgendable(key, fecha, TipoAgendable.EVENTO))));

        tareas.forEach( (key, value) -> {
                  LocalDateTime fechaTarea = value.getFechaVencimiento();
                  /*if (fechaTarea.isAfter(fechaInicio) && fechaTarea.isBefore(fechaFinal)){
                      listaAgendables.add(new RepresentacionAgendable(key, value.getFechaVencimiento(), "Tarea"));
                  }*/
                    if (fechaInicio.compareTo(fechaTarea) <= 0 && fechaFinal.compareTo(fechaTarea) >= 0){
                        listaAgendables.add(new RepresentacionAgendable(key, value.getFechaVencimiento(), TipoAgendable.TAREA));
                    }
        } );

        return listaAgendables.stream().sorted((r1, r2) -> r1.fecha().compareTo(r2.fecha())).collect(Collectors.toCollection(ArrayList::new));
    }

    public Evento buscarEvento(UUID id) {
        return this.eventos.get(id);
    }

    public Tarea buscarTarea(UUID id) {
        return this.tareas.get(id);
    }

    public void eliminarEvento(UUID id) {
        this.eventos.remove(id);
    }

    public void eliminarTarea(UUID id) {
        this.tareas.remove(id);
    }

    public void EditarEvento(UUID id, String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, boolean diaCompleto) {
        if (diaCompleto) {
            fechaInicio = fechaInicio.withHour(0).withMinute(0);
            fechaFinal = fechaFinal.withHour(23).withMinute(59);
        }
        this.eventos.get(id).editarEvento(descripcion, titulo, fechaInicio, fechaFinal);
    }

    public void EditarTarea(UUID id, String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto) {
        this.tareas.get(id).editarTarea(titulo, descripcion, fechaVencimiento, diaCompleto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendario calen = (Calendario) o;

        return tareas.equals(calen.tareas) && eventos.equals(calen.eventos);
    }
}