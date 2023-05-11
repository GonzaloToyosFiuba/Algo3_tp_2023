import java.time.LocalDateTime;

public class Diaria implements TipoFrecuencia{
    int intervalo;

    public Diaria(int intervalo) {
        this.intervalo = intervalo;
    }

    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusDays(intervalo);
    }
}
