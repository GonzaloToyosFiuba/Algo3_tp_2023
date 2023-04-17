import org.junit.Before;
import org.junit.Test;

import java.time.*;
import static org.junit.Assert.*;

public class AlarmaTest {
    @Before
    public void TestConstructor(){
        LocalDateTime fecha = LocalDateTime.now();
        Alarma alarma = new Alarma(fecha,Alarma.TipoAlarma.NOTIFICACION);

        assertEquals(Alarma.TipoAlarma.NOTIFICACION, alarma.getTipo());

        assertEquals(fecha, alarma.getHorarioFechaDisparo());
    }

    @Test
    public void TestSetAlarma(){
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,38), Alarma.TipoAlarma.NOTIFICACION);
        LocalDateTime fechaAlarma = LocalDateTime.of(2023,4,10,18,37);
        assertTrue(alarma.setAlarma(fechaAlarma));
    }

    @Test
    public void TestSetTipo() {
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), Alarma.TipoAlarma.CORREO);

        alarma.setTipo(Alarma.TipoAlarma.NOTIFICACION);
        //
        assertEquals(Alarma.TipoAlarma.NOTIFICACION, alarma.getTipo());
    }

    @Test
    public  void TestDisparo(){
        //Arrange
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), Alarma.TipoAlarma.CORREO);
        LocalDateTime fechaActual = LocalDateTime.of(2023,4,10,18,37,5);
        //
        boolean disparoBien = alarma.disparar(fechaActual);
        //assert
        assertTrue(disparoBien);

    }
}