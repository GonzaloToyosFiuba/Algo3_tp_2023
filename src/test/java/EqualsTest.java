import Calendario.*;
import Control.AdministradorJSON;
import Frecuencias.Diaria;
import Frecuencias.Mensual;
import Frecuencias.Semanal;
import Frecuencias.TipoFrecuencia;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.*;

public class EqualsTest {
    @Test
    public void compararTareas(){
        UUID id = UUID.randomUUID();
        Tarea t1 = new Tarea(id, "Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        t1.agregarAlarma(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);
        t1.agregarAlarma(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);

        Tarea t2 = new Tarea(id, "Basura", "Sacar basura", LocalDateTime.of(2023, 5, 21, 18, 32) , true);
        t2.agregarAlarma(LocalDateTime.of(2024, 5, 21, 18, 32), TipoAlarma.CORREO);
        t2.agregarAlarma(LocalDateTime.of(2023, 5, 21, 18, 32), TipoAlarma.SONIDO);

        assertEquals(t2, t1);
    }

    @Test
    public void compararTipoFrecuencia(){
        TipoFrecuencia t1 = new Diaria(1);
        TipoFrecuencia t2 = new Diaria(1);

        assertEquals(t1, t2);
    }

    @Test
    public void compararTipoFrecuenciaSemanal(){
        TreeSet<DayOfWeek> dias = new TreeSet<>();
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);

        TipoFrecuencia t1 = new Semanal(1, dias);
        TipoFrecuencia t2 = new Semanal(1, dias);

        assertEquals(t1, t2);
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

        assertEquals(e1, e2);
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

        assertEquals(e1, e2);
    }

    @Test
    public void compararCalendario(){
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

        admin.serializar(c1);

        Calendario c2 = admin.deserializar();

        assertEquals(c1, c2);

        c2.agregarEventoFechaLimite("Sacar al perro por la mañana", "Perro", fInicio, fFinal, fLimite, tipo,false);

        assertNotEquals(c1, c2);
    }
}
