package Frecuencias;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Diaria implements TipoFrecuencia {

    @JsonProperty("intervalo")
    int intervalo;

    public Diaria(int intervalo) {
        this.intervalo = intervalo;
    }

    @JsonCreator
    private static Diaria create(@JsonProperty("intervalo") int intervalo) {
        return new Diaria(intervalo);
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusDays(intervalo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diaria diaria = (Diaria) o;
        return intervalo == diaria.intervalo;
    }
}
