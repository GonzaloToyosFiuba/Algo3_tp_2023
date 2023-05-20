import Frecuencias.Diaria;
import Frecuencias.TipoFrecuencia;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class FechaLimiteTest {

    //PRUEBAS CON FRECUENCIA DIARIA
    @Test
    public void TestObtenerRepeticionesEntreEnEventoDeUnDiaFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();

        //Act
        LocalDateTime fechaLimite = LocalDateTime.of(2023, 5, 15, 18, 56);
        FechaLimite e = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, fechaLimite,false);
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
    public void TestObtenerRepeticionesEntreFechaLimiteAlcanzadaFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();

        //Act
        LocalDateTime fechaLimite = LocalDateTime.of(2023, 5, 5, 18, 56);
        FechaLimite e = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, fechaLimite,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }
    @Test
    public void TestObtenerProximaAlarmaUnicaFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);
        LocalDateTime fLimite = LocalDateTime.of(2023, 5, 20, 20, 56);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();

        //Act
        Evento evento = new FechaLimite(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo,fLimite,false);

        evento.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = evento.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }
}
