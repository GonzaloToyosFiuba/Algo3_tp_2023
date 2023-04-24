import org.junit.Before;
import org.junit.Test;

import java.time.*;
import static org.junit.Assert.*;

public class AlarmaTest {
    /*
    @Before
    public void TestConstructor(){
        LocalDateTime fecha = LocalDateTime.now();
        Alarma alarma = new Alarma(fecha,TipoAlarma.NOTIFICACION);

        assertEquals(TipoAlarma.NOTIFICACION, alarma.getTipo());

        assertEquals(fecha, alarma.getHorarioFechaDisparo());
    }

    @Test
    public void TestSetAlarma(){
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,38),TipoAlarma.NOTIFICACION);
        LocalDateTime fechaAlarma = LocalDateTime.of(2023,4,10,18,37);
        assertTrue(alarma.setAlarma(fechaAlarma));
    }

    @Test
    public void TestSetTipo() {
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37),TipoAlarma.CORREO);

        alarma.setTipo(TipoAlarma.NOTIFICACION);
        //
        assertEquals(TipoAlarma.NOTIFICACION, alarma.getTipo());
    }

    @Test
    public  void TestDisparo(){
        //Arrange
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), TipoAlarma.CORREO);
        LocalDateTime fechaActual = LocalDateTime.of(2023,4,10,18,37,5);
        //
        boolean disparoBien = alarma.disparar(fechaActual);
        //assert
        assertTrue(disparoBien);

    }
    */
    @Test
    public void TestDisparoCorreo(){
        //Arrange
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), TipoAlarma.CORREO, 1);
        LocalDateTime fechaActual = LocalDateTime.of(2023,4,10,18,37,5);
        //act
        String envio = alarma.disparar(fechaActual);
        //assert
        assertEquals("Enviando Correo",envio);

    }
    @Test
    public void TestDisparoNotificacion(){
        //Arrange
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), TipoAlarma.NOTIFICACION, 1);
        LocalDateTime fechaActual = LocalDateTime.of(2023,4,10,18,37,5);
        //act
        String envio = alarma.disparar(fechaActual);
        //assert
        assertEquals("Enviando Notificacion",envio);

    }
    @Test
    public void TestDisparoSonido(){
        //Arrange
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), TipoAlarma.SONIDO, 1);
        LocalDateTime fechaActual = LocalDateTime.of(2023,4,10,18,37,5);
        //act
        String envio = alarma.disparar(fechaActual);
        //assert
        assertEquals("Enviando Sonido",envio);

    }

    @Test
    public void TestNoDisparo(){
        //Arrange
        Alarma alarma = new Alarma(LocalDateTime.of(2023,4,10,18,37), TipoAlarma.SONIDO, 1);
        LocalDateTime fechaActual = LocalDateTime.of(2023,4,10,18,36);
        //act
        String envio = alarma.disparar(fechaActual);
        //assert
        assertEquals("No se disparo",envio);

    }


}