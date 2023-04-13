import java.time.*;
import java.time.temporal.ChronoUnit;

public class Alarma {
    private LocalDateTime horarioFechaDisparo;
    public static enum  TipoAlarma{
        NOTIFICACION,
        SONIDO,
        CORREO
    }
    private TipoAlarma tipo;
    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipo = tipo;
    }

    /*
        PRE: Se le envia la hora y fecha de disparo
        POST: Se le setea dicha fecha y hora a la alarma y devuelve un true
        */
    public boolean setAlarma(LocalDateTime disparo){
        this.horarioFechaDisparo =  disparo;
        return true;
    }

    public LocalDateTime getHorarioFechaDisparo() {
        return horarioFechaDisparo;
    }

    public TipoAlarma getTipo() {
        return tipo;
    }

    public void setTipo(TipoAlarma tipo) {
        this.tipo = tipo;
    }

    /*
            PRE: Se le ingresa la hora actual según ubicación en el mundo
            POST: Devuelve true en caso de que la fecha
        */
    public boolean disparar(LocalDateTime fechaActual){
        boolean seDispara = false;
        if (this.horarioFechaDisparo.compareTo(fechaActual.truncatedTo(ChronoUnit.MINUTES)) == 0){
            seDispara = true;
        }
        return seDispara;
    }
}
