import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TareaTest {
    @Before
    public void TestConstructor(){
        LocalDateTime fechaVecimiento = LocalDateTime.of(2023,4,11,10,00);
        boolean diaCompleto = false;
        Tarea tarea = new Tarea(fechaVecimiento, diaCompleto);
    }

    @Test

    public void TestEstablecerAlarma(){
        //
        LocalDateTime fechaVecimiento = LocalDateTime.of(2023,4,11,10,00);
        boolean diaCompleto = false;
        Tarea tarea = new Tarea(fechaVecimiento, diaCompleto);
        LocalDateTime fechaAlarma= LocalDateTime.of(2023,4,11,9,30);
        Alarma alarma = new Alarma(fechaAlarma, Alarma.TipoAlarma.NOTIFICACION);
        //
        tarea.agregarAlarma(alarma);
        //
        assertEquals(alarma,tarea.obtenerUltimaAlarma());
    }
}