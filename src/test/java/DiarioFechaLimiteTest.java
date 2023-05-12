import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiarioFechaLimiteTest {

    /*@Test
    public void TestObtenerRepeticionesEntreEnEventoDeFechalimiteConLimiteAlcanzado() {
        // Cuando f2 > fechaLimite
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 10, 10, 18, 56);

        Repeticion repe = new DiarioFechaLimite(2, LocalDateTime.of(2023, 5, 9, 18, 56));
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
    public void TestObtenerRepeticionesEntreEnEventoDeFechalimiteSinLimiteAlcanzado() {
        // cuando f2 < fechaLimite
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 9, 18, 56);

        Repeticion repe = new DiarioFechaLimite(2, LocalDateTime.of(2023, 6, 1, 18, 56));

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
    public void TestObtenerRepeticionesEntreEnEventoInfinito() {
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 12, 4, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 12, 10, 18, 56);

        Repeticion repe = new DiarioFechaLimite(2, LocalDateTime.MAX);

        //Act
        Evento e = new Evento(15, "Sacar al perro por la mañana", "Perro", repe, fInicio, fFinal);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 12, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 12, 6, 18, 56);
        LocalDateTime fechaEsperada3 = LocalDateTime.of(2023, 12, 8, 18, 56);
        LocalDateTime fechaEsperada4 = LocalDateTime.of(2023, 12, 10, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);
        fechasEsperadas.add(fechaEsperada3);
        fechasEsperadas.add(fechaEsperada4);
        //assert
        assertEquals(fechasEsperadas, fechas);
    }*/
}