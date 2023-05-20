package Frecuencias;

import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Anual implements TipoFrecuencia {
    @JsonProperty("intervalo")
    int intervalo;

    public Anual(int intervalo) {
        this.intervalo = intervalo;
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusYears(intervalo);
    }
}
