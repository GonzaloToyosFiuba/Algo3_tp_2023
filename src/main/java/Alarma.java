import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Alarma implements Comparable{
    private LocalDateTime horarioFechaDisparo;
    private TipoAlarma tipoAlarma;

    private boolean repetible;
    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo, boolean repetible) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
        this.repetible = repetible;
    }

    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
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

    @Override
    public int compareTo(Object o) {
        return this.horarioFechaDisparo.compareTo( ((Alarma)o).getHorarioFechaDisparo() );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarma alarma = (Alarma) o;
        return repetible == alarma.repetible && Objects.equals(horarioFechaDisparo, alarma.horarioFechaDisparo) && tipoAlarma == alarma.tipoAlarma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(horarioFechaDisparo, tipoAlarma, repetible);
    }
}
