package Frecuencias;

import Frecuencias.TipoFrecuencia;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mensual implements TipoFrecuencia {
    @JsonProperty("intervalo")
    int intervalo;

    public Mensual(int intervalo) {
        this.intervalo = intervalo;
    }

    @JsonCreator
    private static Mensual create(@JsonProperty("intervalo") int intervalo) {
        return new Mensual(intervalo);
    }
    @Override
    public LocalDateTime obtenerProximoDia(LocalDateTime dia) {
        return dia.plusMonths(intervalo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensual mensual = (Mensual) o;
        return intervalo == mensual.intervalo;
    }
}
