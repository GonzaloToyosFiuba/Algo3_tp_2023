import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void TestObtenerAlarmaProxima(){
        // Arrange
        LocalDateTime fvenciomiento = LocalDateTime.of(2023, 5, 4, 5, 50);
        Tarea tarea = new Tarea(12,"PERRO","sacar a pasear",fvenciomiento,false);
        tarea.agregarAlarma(30,TipoAlarma.NOTIFICACION);
        //tarea.agregarAlarma(30,TipoAlarma.NOTIFICACION);
        //act
        ArrayList<Alarma> alarmasProximas = tarea.obtenerProximaAlarma(LocalDateTime.of(2023, 5, 4, 5, 10));
        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 5, 20),TipoAlarma.NOTIFICACION);
        alarmasEsperadas.add(alarmaEsperada);
        //asset
        assertEquals(alarmasEsperadas,alarmasProximas);
    }
    /*
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
        Alarma alarma = new Alarma(fechaAlarma, TipoAlarma.NOTIFICACION);
        //
        tarea.agregarAlarma(alarma);
        //
        assertEquals(alarma,tarea.obtenerUltimaAlarma());
    }

     */
}