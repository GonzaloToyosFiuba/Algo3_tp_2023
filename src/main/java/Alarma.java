import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Alarma implements Comparable{
    private LocalDateTime horarioFechaDisparo;
    private TipoAlarma tipoAlarma;
    private int id;
    private boolean repetible;
    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo, boolean repetible, int id) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
        this.repetible = repetible;
        this.id = id;
    }

    public Alarma(LocalDateTime horarioFechaDisparo, TipoAlarma tipo, int id) {
        this.horarioFechaDisparo = horarioFechaDisparo;
        this.tipoAlarma = tipo;
        this.id = id;
    }

    public String disparar(LocalDateTime fechaActual){
        String seDispara = "No se disparo";
        if (this.horarioFechaDisparo.compareTo(fechaActual.truncatedTo(ChronoUnit.MINUTES)) == 0){

            seDispara = tipoAlarma.dipararAlarma();;
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

    public int getId(){
        return this.id;
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
        return id == alarma.id && repetible == alarma.repetible && Objects.equals(horarioFechaDisparo, alarma.horarioFechaDisparo) && tipoAlarma == alarma.tipoAlarma;
    }

    @Override
    public int hashCode() {
        return Objects.hash(horarioFechaDisparo, tipoAlarma, id, repetible);
    }
}
