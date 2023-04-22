import java.time.*;
import java.time.temporal.ChronoUnit;

public class Alarma {
    private LocalDateTime horarioFechaDisparo;
    private TipoAlarma tipoAlarma;

    private boolean repetible;
    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo, boolean repetible) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
        this.repetible = repetible;
    }

    public boolean disparar(LocalDateTime fechaActual){
        boolean seDispara = false;
        if (this.horarioFechaDisparo.compareTo(fechaActual.truncatedTo(ChronoUnit.MINUTES)) == 0){
            tipoAlarma.dipararAlarma();
            seDispara = true;
        }
        return seDispara;
    }

    public LocalDateTime getHorarioFechaDisparo() {
        return horarioFechaDisparo;
    }

    public TipoAlarma getTipo() {
        return tipoAlarma;
    }

    public boolean esRepetible(){
        return this.repetible;
    }

    public void setTipo(TipoAlarma tipo) {
        this.tipoAlarma = tipo;
    }
    public boolean setAlarma(LocalDateTime disparo){
        this.horarioFechaDisparo =  disparo;
        return true;
    }
}
