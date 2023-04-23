import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Tarea {
    private final int id;
    private String nombre, descripcion;
    private boolean completado;
    private LocalDateTime fechaVencimiento;
    private ArrayList<Alarma> alarmas;
    private boolean diaCompleto;

    public Tarea(int id, String nombre, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.diaCompleto = diaCompleto;
        this.alarmas = new ArrayList<Alarma>();
    }

    public void completar(){
        this.completado = true;
    }

    public ArrayList<Alarma> obtenerProximaAlarma(LocalDateTime horarioActual){
        //Alarma alarmaMinima = Collections.min(this.alarmas); // 5pm yo busco los mayor o igual a 5pm mas cercano
        ArrayList<Alarma> alarmasAux = new ArrayList<>();

        for (Alarma alarma:this.alarmas) { // Esto para buscar solo alarmas que todavia no ocurrieron
            if(alarma.getHorarioFechaDisparo().compareTo(horarioActual) >= 0){
                alarmasAux.add(alarma);
            }
        }
        Alarma alarmaMinima = Collections.min(alarmasAux);
        ArrayList<Alarma> alarmasRetorno = new ArrayList<>();

        for (Alarma alarma:alarmasAux) { // si hay alarmas repetidas
            if(alarma.compareTo(alarmaMinima) == 0){
                alarmasRetorno.add(alarma);
            }
        }

        return alarmasRetorno;
    }

    public void agregarAlarma(int minutosAntes, TipoAlarma tipo){
        LocalDateTime fechaDisparo = fechaVencimiento.minusMinutes(minutosAntes);
        Alarma alarma = new Alarma(fechaDisparo, tipo);
        this.alarmas.add(alarma);
    }

    public void agregarAlarma(LocalDateTime fechaDisparo, TipoAlarma tipo){
        Alarma alarma = new Alarma(fechaDisparo, tipo);
        this.alarmas.add(alarma);
    }
}
