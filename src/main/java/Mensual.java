import java.time.LocalDateTime;

public class Mensual implements TipoFrecuencia{
    int intervalo;

    public Mensual(int intervalo) {
        this.intervalo = intervalo;
    }

    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusMonths(intervalo);
    }
}
