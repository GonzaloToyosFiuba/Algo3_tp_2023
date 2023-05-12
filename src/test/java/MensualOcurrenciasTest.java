import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
public class MensualOcurrenciasTest {
    @Test
    public void TestObtenerRepeticionesEntreBasico(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10);
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
    public void TestObtenerRepeticionesEntreConLimiteAlcanzado(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 1);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();
        LocalDateTime fechaEsperada1 = LocalDateTime.of(2023, 5, 4, 18, 56);

        fechasEsperadas.add(fechaEsperada1);

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasSinRepeticiones(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2024, 5, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2024, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3);
        ArrayList<LocalDateTime> fechas = e.obtenerRepeticionesEntre(f1, f2);

        ArrayList<LocalDateTime> fechasEsperadas = new ArrayList<>();

        //assert
        assertEquals(fechasEsperadas, fechas);
    }

    @Test
    public void TestObtenerRepeticionesEntreFechasEnElmedio(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        LocalDateTime f1 = LocalDateTime.of(2023, 6, 1, 18, 56);
        LocalDateTime f2 = LocalDateTime.of(2023, 8, 10, 18, 55);

        TipoFrecuencia tipo = new Mensual(1);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 3);
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
    public void TestObtenerProximaAlarmaUnica(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10);
        e.agregarAlarmaUnica(LocalDateTime.of(2023, 5, 4, 18, 20),TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 4, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 4, 18, 20), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntes(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 0, 26);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 4, 56);

        Repeticion repe = new MensualOcurrencias(2, 10);
        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10);
        e.agregarAlarmaRepetible(30, TipoAlarma.SONIDO);

        ArrayList<Alarma> alarmas = e.obtenerProximaAlarma( LocalDateTime.of(2023, 5, 3, 18, 15));

        ArrayList<Alarma> alarmasEsperadas = new ArrayList<>();
        Alarma alarmaEsperada = new Alarma(LocalDateTime.of(2023, 5, 3, 23, 56), TipoAlarma.SONIDO,0);
        alarmasEsperadas.add(alarmaEsperada);

        //assert
        assertEquals(alarmasEsperadas, alarmas);
    }

    @Test
    public void TestObtenerProximaAlarmaMinutosAntesYFija(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10);
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
    public void TestObtenerProximaAlarmaVariasMinutosAntes(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10);
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
    public void TestObtenerProximaAlarmaDeUnaRepeticion(){
        //Arrange
        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 5, 4, 20, 56);

        TipoFrecuencia tipo = new Mensual(2);

        //Act
        CantidadMax e = new CantidadMax(15, "Sacar al perro por la mañana", "Perro", fInicio, fFinal, tipo, 10);
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
}
