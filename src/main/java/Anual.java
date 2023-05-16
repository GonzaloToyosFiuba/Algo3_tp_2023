import java.time.LocalDateTime;

public class Anual implements TipoFrecuencia {
    int intervalo;

    public Anual(int intervalo) {
        this.intervalo = intervalo;
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusYears(intervalo);
    }
}
