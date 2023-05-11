import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public  abstract class Evento {
    protected int id;
    protected String descripcion;
    protected String titulo;

    protected LocalDateTime fechaInicio;

    protected LocalDateTime fechaFinal;

    protected ArrayList<Alarma> alarmas;

    protected TipoFrecuencia tipoFrecuencia;
    private int contadorIdAlarmas;

    public Evento(int id, String descripcion, String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFinal, TipoFrecuencia tipoFrecuencia) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
        this.contadorIdAlarmas = 0;
        this.alarmas = new ArrayList<Alarma>();
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public int getId(){
      return this.id;
    }

    public void  agregarAlarmaRepetible(int minutosAntes, TipoAlarma tipo){
        // crea una alarma mandando la fecha original del eveto menos los minutos antes y con el tipo , en la parte repetible es true
        LocalDateTime fechaDisparo = fechaInicio.minusMinutes(minutosAntes);
        Alarma nuevaAlarma = new Alarma(fechaDisparo, tipo, true, this.contadorIdAlarmas);
        alarmas.add(nuevaAlarma);
        this.contadorIdAlarmas++;
    }

    public void agregarAlarmaUnica(LocalDateTime fechaDisparo, TipoAlarma tipo){
        // crea una alarma que la hora de disparado dada y si tipo, en la parte repeticion es False.
        Alarma nuevaAlarma = new Alarma(fechaDisparo, tipo, this.contadorIdAlarmas);
        alarmas.add(nuevaAlarma);
        this.contadorIdAlarmas++;
    }

    public abstract ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual);

    public abstract ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2);
    public void eliminarAlarma(int id){
        int posicion = 0;

        for(Alarma alarma:this.alarmas){
            if (alarma.getId() == id){
                alarmas.remove(posicion);
                break;
            }
            posicion++;
        }
    }

    public void editarEvento(String descripcion, String titulo, Repeticion tipoRepeticion, LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        if (fechaInicio != this.fechaInicio){ // Esto es momentaneo , hay que hacer que las alarmas se pueda reconfigurar respecto a un cambio de fecha
            alarmas.clear();
            this.contadorIdAlarmas = 0;
        }
        this.descripcion = descripcion;
        this.titulo = titulo;
       // this.tipoRepeticion = tipoRepeticion;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;

    }
}
