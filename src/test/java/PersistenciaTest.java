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

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersistenciaTest {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void serializacionTipoFrecuencia(){
        String serializacionEsperada = "{\"tipo\":\"diaria\",\"intervalo\":5}";
        String serializacionObtenida = "";

        TipoFrecuencia tipo = new Diaria(5);

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(tipo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(serializacionEsperada, serializacionObtenida);
    }

    @Test
    public void serializacionTipoFrecuenciaConSemanal(){
        String serializacionEsperada = "{\"tipo\":\"semanal\",\"intervalo\":1,\"dias\":[\"MONDAY\",\"THURSDAY\"]}";
        String serializacionObtenida = "";

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(tipo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(serializacionEsperada, serializacionObtenida);
    }

    @Test
    public void persistenciaTarea(){
        Tarea t = new Tarea(UUID.randomUUID(), "Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        t.agregarAlarma(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        t.agregarAlarma(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        try {
            String serializacionObtenida =  objectMapper.writeValueAsString(t);
            System.out.println(serializacionObtenida);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void persistenciaEventoCantidadMax(){
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(2, dias);
        UUID id = UUID.randomUUID();

        Evento e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 5,false);

        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        e.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        try {
            String serializacionObtenida =  objectMapper.writeValueAsString(e);
            System.out.println(serializacionObtenida);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void persistenciaEventoFechaLimite(){
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 7, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(4);
        UUID id = UUID.randomUUID();

        Evento e = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, fLimite,false);

        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        e.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        try {
            String serializacionObtenida =  objectMapper.writeValueAsString(e);
            System.out.println(serializacionObtenida);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void persistenciaCalendario(){
        Calendario c = new Calendario();
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 7, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(4);

        TreeSet<DayOfWeek> dias = new TreeSet<>();
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo2 = new Semanal(1, dias);

        c.agregarTarea("Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        c.agregarTarea("Perro", "Pasear a Lio", LocalDateTime.of(2024, 5, 1, 18, 32) , false);
        c.agregarEventoFechaLimite("Sacar al perro por la mañana", "Perro", fInicio, fFinal, fLimite, tipo,false);
        c.agregarEventoCantMax("Sacar al perro por la mañana", "Perro", fInicio, fFinal, 3, tipo2,false);

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String serializacionObtenida =  objectMapper.writeValueAsString(c);
            System.out.println(serializacionObtenida);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void DeserializacionFrecuencia(){

        try {
            TipoFrecuencia miObjeto = objectMapper.readValue(new File("ejemplo.json"), TipoFrecuencia.class);

            System.out.println(miObjeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void DeserializacionAlarma(){

        try {
            Alarma miObjeto = objectMapper.readValue(new File("ejemplo.json"), Alarma.class);

            System.out.println(miObjeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DeserializarEvento(){
        try {
            Evento miObjeto = objectMapper.readValue(new File("ejemplo.json"), Evento.class);

            System.out.println(miObjeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DeserializarTarea(){
        try {
            Tarea miObjeto = objectMapper.readValue(new File("ejemplo.json"), Tarea.class);

            System.out.println(miObjeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DeserializarCalendario(){
        try {
            Calendario miObjeto = objectMapper.readValue(new File("ejemplo.json"), Calendario.class);

            System.out.println(miObjeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void administradorJSON(){
        Calendario c = new Calendario();
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 7, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(4);

        TreeSet<DayOfWeek> dias = new TreeSet<>();
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo2 = new Semanal(1, dias);

        c.agregarTarea("Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32), true);
        c.agregarTarea("Perro", "Pasear a Lio", LocalDateTime.of(2024, 5, 1, 18, 32), false);
        c.agregarEventoFechaLimite("Sacar al perro por la mañana", "Perro", fInicio, fFinal, fLimite, tipo,false);
        c.agregarEventoCantMax("Sacar al perro por la mañana", "Perro", fInicio, fFinal, 3, tipo2,false);

        AdministradorJSON admin = new AdministradorJSON();

        admin.serializar(c);

        Calendario nuevo = admin.deserializar();
    }

    @Test
    public void compararTareas(){
        UUID id = UUID.randomUUID();
        Tarea t1 = new Tarea(id, "Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        t1.agregarAlarma(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        t1.agregarAlarma(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        Tarea t2 = new Tarea(id, "Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        t2.agregarAlarma(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);
        t2.agregarAlarma(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);

        assertTrue(t1.equals(t2));
    }

    @Test
    public void compararTipoFrecuencia(){
        TreeSet<DayOfWeek> dias = new TreeSet<>();
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);

        TipoFrecuencia t1 = new Semanal(1, dias);
        TipoFrecuencia t2 = new Semanal(1, dias);

        assertTrue(t1.equals(t2));
    }

    @Test
    public void compararEventosCantidadMax(){
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(2, dias);
        UUID id = UUID.randomUUID();

        Evento e1 = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 5,false);
        Evento e2 = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 5,false);

        e1.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        e2.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        e2.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);
        e1.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        assertTrue(e1.equals(e2));
    }

    @Test
    public void compararEventosFechaLimite(){
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 7, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(4);
        UUID id = UUID.randomUUID();

        Evento e1 = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, fLimite,false);
        Evento e2 = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, fLimite,false);

        e1.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        e2.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        e2.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);
        e1.agregarAlarmaUnica(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        assertTrue(e1.equals(e2));
    }

}