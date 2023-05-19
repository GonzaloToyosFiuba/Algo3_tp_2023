import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Mensual implements TipoFrecuencia{
    @JsonProperty("intervalo")
    int intervalo;

    public Mensual(int intervalo) {
        this.intervalo = intervalo;
    }

    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusMonths(intervalo);
    }
}
