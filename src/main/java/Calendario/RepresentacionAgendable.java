package Calendario;

import java.time.LocalDateTime;
import java.util.UUID;

public record RepresentacionAgendable(UUID id, LocalDateTime fecha, String tipo) {

}
