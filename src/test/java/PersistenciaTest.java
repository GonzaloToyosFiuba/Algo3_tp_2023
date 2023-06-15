import Calendario.*;
import Control.AdministradorJSON;
import Frecuencias.Diaria;
import Frecuencias.Mensual;
import Frecuencias.Semanal;
import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.*;

public class PersistenciaTest {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void serializacionYdeserializacionTipoFrecuencia(){
        TipoFrecuencia tipoOriginal = new Diaria(5);

        String serializacionObtenida;
        TipoFrecuencia tipoDeserializado = null;

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(tipoOriginal);
            tipoDeserializado = objectMapper.readValue(serializacionObtenida, TipoFrecuencia.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(tipoOriginal, tipoDeserializado);
    }

    @Test
    public void serializacionYdeserializacionTipoFrecuenciaConSemanal(){
        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipoOriginal = new Semanal(1, dias);

        String serializacionObtenida;
        TipoFrecuencia tipoDeserializado = null;

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(tipoOriginal);
            tipoDeserializado = objectMapper.readValue(serializacionObtenida, TipoFrecuencia.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(tipoOriginal, tipoDeserializado);
    }

    @Test
    public void serializacionYdeserializacionTarea(){
        UUID id = UUID.fromString("feeb03e4-7410-4215-978f-8ab0bcc39ceb");

        Tarea tareaOriginal = new Tarea(id, "Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        tareaOriginal.agregarAlarma(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        tareaOriginal.agregarAlarma(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        String serializacionObtenida;
        Tarea tareaDeserializada = null;
        try {
            serializacionObtenida =  objectMapper.writeValueAsString(tareaOriginal);
            System.out.println(serializacionObtenida);
            tareaDeserializada = objectMapper.readValue(serializacionObtenida, Tarea.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(tareaOriginal, tareaDeserializada);
    }

    @Test
    public void serializacionYdeserializacionEventoCantidadMax(){
        UUID id = UUID.fromString("abcb03e4-1001-4215-978f-8ab0bcc39ceb");
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(2, dias);

        Evento eventoOriginal = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 5,false);

        eventoOriginal.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        eventoOriginal.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        String serializacionObtenida;
        Evento eventoDeserializado = null;

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(eventoOriginal);
            eventoDeserializado = objectMapper.readValue(serializacionObtenida, Evento.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        assertEquals(eventoOriginal, eventoDeserializado);
    }

    @Test
    public void serializacionYdeserializacionEventoFechaLimite(){
        UUID id = UUID.fromString("abcb03e4-1001-4215-978f-8ab0bcc39ceb");
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 7, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(4);

        Evento eventoOriginal = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, fLimite,false);

        eventoOriginal.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        eventoOriginal.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        String serializacionObtenida;
        Evento eventoDeserializado = null;

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(eventoOriginal);
            eventoDeserializado = objectMapper.readValue(serializacionObtenida, Evento.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        assertEquals(eventoOriginal, eventoDeserializado);
    }

    @Test
    public void serializacionYdeserializacionCalendario() {
        Calendario c1 = new Calendario();
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 7, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(4);

        TreeSet<DayOfWeek> dias = new TreeSet<>();
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo2 = new Semanal(1, dias);

        c1.agregarTarea("Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        c1.agregarTarea("Perro", "Pasear a Lio", LocalDateTime.of(2024, 5, 1, 18, 32) , false);
        c1.agregarEventoFechaLimite("Sacar al perro por la mañana", "Perro", fInicio, fFinal, fLimite, tipo,false);
        c1.agregarEventoCantMax("Sacar al perro por la mañana", "Perro", fInicio, fFinal, 3, tipo2,false);

        AdministradorJSON admin = new AdministradorJSON();

        Writer w = new StringWriter();
        Calendario c2 = null;
        Reader r;
        try {
            admin.serializar(c1, w);
            w.close();
            r = new StringReader(w.toString());
            c2 = admin.deserializar(r);
        } catch (IOException e){
            e.printStackTrace();
        }

        assertEquals(c1, c2);
    }

}
