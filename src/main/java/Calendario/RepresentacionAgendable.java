package Calendario;

import java.time.LocalDateTime;
import java.util.UUID;

public record RepresentacionAgendable (LocalDateTime fecha, Agendable agendable){

}
