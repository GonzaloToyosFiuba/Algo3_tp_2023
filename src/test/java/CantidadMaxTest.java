import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CantidadMaxTest {
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
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
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
    public void TestObtenerRepeticionesEntreConLimiteAlcanzadoFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 2,false);
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
    public void TestObtenerRepeticionesEntreFechasSinRepeticionesFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2024, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2024, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();
        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 2,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasEnElMedioFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 7, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();
        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 8, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerProximaAlarmaUnicaFrecuenciaDiaria(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Diaria(2);
        UUID id = UUID.randomUUID();
        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    //FRECUENCIA MENSUAL
    @Test
    public void TestObtenerRepeticionesEntreBasicoFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 7, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreConLimiteAlcanzadoFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 1,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasSinRepeticionesFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2024, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2024, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3, false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasEnElmedioFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 6, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3, false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 6, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 7, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerProximaAlarmaUnicaFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 0, 26);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 4, 56);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();
        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 3, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 3, 23, 56), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesYFijaFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);//id = 1

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,1);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaVariasMinutosAntesFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaRepetible(15, TipoAlarma.SONIDO);//id = 1
        e.agregarAlarmaRepetible(50, TipoAlarma.SONIDO);//id = 2

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 26), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaDeUnaRepeticionFrecuenciaMensual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaRepetible(15, TipoAlarma.SONIDO);//id = 1
        e.agregarAlarmaRepetible(50, TipoAlarma.SONIDO);//id = 2

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 7, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 7, 4, 18, 26), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    //FRECUENCIA ANUAL
    @Test
    public void TestObtenerRepeticionesEntreBasicoFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2024, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2024, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreConLimiteAlcanzadoFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2024, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 1,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasSinRepeticionesFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2018, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2018, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 6, 10, 18, 55);

        TipoFrecuencia tipo = new Anual(2);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasEnElmedioFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2025, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2025, 5, 10, 18, 55);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3, false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2025, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerProximaAlarmaUnicaFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 2,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 26), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesYFijaFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();
        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 2,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);//id = 1

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,1);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaVariasMinutosAntesFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaRepetible(15, TipoAlarma.SONIDO);//id = 1
        e.agregarAlarmaRepetible(50, TipoAlarma.SONIDO);//id = 2

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 26), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaDeUnaRepeticionFrecuenciaAnual(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Anual(1);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaRepetible(15, TipoAlarma.SONIDO);//id = 1
        e.agregarAlarmaRepetible(50, TipoAlarma.SONIDO);//id = 2

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 12, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2024, 5, 4, 18, 6), TipoAlarma.SONIDO,2);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    //FRECUENCIA SEMANAL
    @Test
    public void TestObtenerRepeticionesEntreBasicoFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 7, 20, 55);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        dias.add(DayOfWeek.SUNDAY);
        dias.add(DayOfWeek.FRIDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();
        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 5, 5, 18, 56);
        LocalDateTime fechaEsperada3 = LocalDateTime.of(2023, 5, 7, 18, 56);


        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);
        fechasEsperadas.add(fechaEsperada3);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreConLimiteAlcanzadoFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        dias.add(DayOfWeek.SUNDAY);
        dias.add(DayOfWeek.FRIDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 1,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasSinRepeticionesFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2018, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2018, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 6, 10, 18, 55);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        dias.add(DayOfWeek.SUNDAY);
        dias.add(DayOfWeek.FRIDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasEnElmedioFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 10, 18, 55);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 5, 8, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesConIntervaloFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 5, 30, 18, 55);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(2, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 5,false);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fechaEsperada2 = LocalDateTime.of(2023, 5, 15, 18, 56);
        LocalDateTime fechaEsperada3 = LocalDateTime.of(2023, 5, 18, 18, 56);
        LocalDateTime fechaEsperada4 = LocalDateTime.of(2023, 5, 29, 18, 56);

        fechasEsperadas.add(fechaEsperada1);
        fechasEsperadas.add(fechaEsperada2);
        fechasEsperadas.add(fechaEsperada3);
        fechasEsperadas.add(fechaEsperada4);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerProximaAlarmaUnicaFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3,false);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 2,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 26), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesYFijaFrecuenciaSemanal(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TreeSet<DayOfWeek> dias = new TreeSet<>();

        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        TipoFrecuencia tipo = new Semanal(1, dias);
        UUID id = UUID.randomUUID();

        //Act
        CantidadMax e = new CantidadMax(id, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 2,false);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);//id = 0
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 10, 18, 20),TipoAlarma.SONIDO);//id = 1

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 7, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 8, 18, 26), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

}
