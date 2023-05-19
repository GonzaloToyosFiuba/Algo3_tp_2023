import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Diaria.class, name = "diaria"),
        @JsonSubTypes.Type(value = Semanal.class, name = "semanal"),
        @JsonSubTypes.Type(value = Mensual.class, name = "mensual"),
        @JsonSubTypes.Type(value = Anual.class, name = "anual")
})
public interface TipoFrecuencia {
    public LocalDateTime obtenerProximoDia(LocalDateTime dia);
}
