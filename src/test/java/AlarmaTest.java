import Calendario.Alarma;
import Calendario.TipoAlarma;
import org.junit.Test;

import java.time.*;
import static org.junit.Assert.*;

public class AlarmaTest {
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