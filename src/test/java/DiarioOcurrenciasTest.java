import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DiarioOcurrenciasTest {
    @Test
    public void TestObtenerRepeticionesEntreEnEventoDeUnDia(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        Repeticion repe = new DiarioOcurrencias(2, 10);

        //Act
        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 5, 6, 18, 56);
        LocalDateTime fechaEsperada3 = LocalDateTime.of(2023, 5, 8, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);
        fechasEsperadas.add(fechaEsperada3);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreConLimiteAlcanzado(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        Repeticion repe = new DiarioOcurrencias(2, 2);

        //Act
        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 5, 6, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasSinRepeticiones(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2024, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2024, 5, 10, 18, 55);

        Repeticion repe = new DiarioOcurrencias(2, 2);

        //Act
        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasEnElMedio(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 7, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        Repeticion repe = new DiarioOcurrencias(2, 10);

        //Act
        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 8, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerProximaAlarmaUnica(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 7, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        Repeticion repe = new DiarioOcurrencias(2, 10);

        //Act
        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }
}
