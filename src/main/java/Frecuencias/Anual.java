package Frecuencias;

import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Anual implements TipoFrecuencia {
    @JsonProperty("intervalo")
    int intervalo;

    public Anual(int intervalo) {
        this.intervalo = intervalo;
    }
    @JsonCreator
    private static Anual create(@JsonProperty("intervalo") int intervalo) {
        return new Anual(intervalo);
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusYears(intervalo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anual anual = (Anual) o;
        return intervalo == anual.intervalo;
    }
}
