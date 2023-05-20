import Calendario.Alarma;
import Calendario.Tarea;
import Calendario.TipoAlarma;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void TestObtenerAlarmaProxima(){
        // Arrange
        LocalDateTime fVencimiento = LocalDateTime.of(2023, 5, 4, 5, 50);
        UUID id = UUID.randomUUID();
        Tarea tarea = new Tarea(id,"PERRO","sacar a pasear", fVencimiento,false);
        tarea.agregarAlarma(30,TipoAlarma.NOTIFICACION);

        //act
        ArrayList<Alarma> alarmasProximas = tarea.obtenerProximaAlarma(LocalDateTime.of(2023, 5, 4, 5, 10));
        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 5, 20),TipoAlarma.NOTIFICACION,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmasProximas);
    }

    @Test
    public void TestObtenerAlarmaProximaFija(){
        // Arrange
        LocalDateTime fVencimiento = LocalDateTime.of(2023, 5, 4, 5, 50);
        UUID id = UUID.randomUUID();
        Tarea tarea = new Tarea(id,"PERRO","sacar a pasear", fVencimiento,false);
        tarea.agregarAlarma(LocalDateTime.of(2023, 5, 4, 5, 40), TipoAlarma.SONIDO);

        //act
        ArrayList<Alarma> alarmasProximas = tarea.obtenerProximaAlarma(LocalDateTime.of(2023, 5, 4, 5, 10));
        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 5, 40), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmasProximas);
    }

    @Test
    public void TestObtenerAlarmaProximaFijaYVariable(){
        // Arrange
        LocalDateTime fVencimiento = LocalDateTime.of(2023, 5, 4, 5, 50);
        UUID id = UUID.randomUUID();
        Tarea tarea = new Tarea(id,"PERRO","sacar a pasear", fVencimiento,false);
        tarea.agregarAlarma(LocalDateTime.of(2023, 5, 4, 5, 40), TipoAlarma.SONIDO); // id = 0
        tarea.agregarAlarma(30, TipoAlarma.SONIDO); // id = 1

        //act
        ArrayList<Alarma> alarmasProximas = tarea.obtenerProximaAlarma(LocalDateTime.of(2023, 5, 4, 5, 10));
        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 5, 20), TipoAlarma.SONIDO,1);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmasProximas);
    }

    @Test
    public void TestObtenerAlarmaProximaConVariasAlarmas(){
        // Arrange
        LocalDateTime fVencimiento = LocalDateTime.of(2023, 5, 4, 5, 50);
        UUID id = UUID.randomUUID();
        Tarea tarea = new Tarea(id,"PERRO","sacar a pasear", fVencimiento,false);
        tarea.agregarAlarma(LocalDateTime.of(2023, 5, 4, 5, 40), TipoAlarma.SONIDO); // id = 0
        tarea.agregarAlarma(LocalDateTime.of(2023, 4, 4, 5, 40), TipoAlarma.SONIDO);//  id = 1
        tarea.agregarAlarma(30, TipoAlarma.SONIDO); // id = 2
        tarea.agregarAlarma(20, TipoAlarma.SONIDO); // id = 3

        //act
        ArrayList<Alarma> alarmasProximas = tarea.obtenerProximaAlarma(LocalDateTime.of(2023, 5, 4, 5, 10));
        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 5, 20), TipoAlarma.SONIDO,2);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmasProximas);
    }

    @Test
    public void TestObtenerAlarmaProximaConFechaActualPosterior(){
        // Arrange
        LocalDateTime fVencimiento = LocalDateTime.of(2023, 5, 4, 5, 50);
        UUID id = UUID.randomUUID();
        Tarea tarea = new Tarea(id,"PERRO","sacar a pasear", fVencimiento,false);
        tarea.agregarAlarma(LocalDateTime.of(2023, 5, 4, 5, 40), TipoAlarma.SONIDO);
        tarea.agregarAlarma(20, TipoAlarma.SONIDO);

        //act
        ArrayList<Alarma> alarmasProximas = tarea.obtenerProximaAlarma(LocalDateTime.of(2024, 5, 4, 5, 10));
        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();

        //assert
        assertEquals(alarmasEsperadas, alarmasProximas);
    }
// Prueba
}