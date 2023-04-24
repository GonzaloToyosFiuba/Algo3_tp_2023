import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Tarea {
    private final int id;
    private String titulo, descripcion;
    private boolean completado;
    private LocalDateTime fechaVencimiento;
    private ArrayList<Alarma> alarmas;
    private boolean diaCompleto;

    private int contadorIdAlamas;

    public Tarea(int id, String titulo, String descripcion, LocalDateTime fechaVencimiento, boolean diaCompleto) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.diaCompleto = diaCompleto;
        this.alarmas = new ArrayList<Alarma>();
        this.contadorIdAlamas = 0;
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

        ArrayList<Alarma> alarmasRetorno = new ArrayList<>();

        if (!alarmasAux.isEmpty()){
            Alarma alarmaMinima = Collections.min(alarmasAux);

            for (Alarma alarma:alarmasAux) { // si hay alarmas repetidas
                if(alarma.compareTo(alarmaMinima) == 0){
                    alarmasRetorno.add(alarma);
                }
            }
        }

        return alarmasRetorno;
    }

    public void agregarAlarma(int minutosAntes, TipoAlarma tipo){
        LocalDateTime fechaDisparo = fechaVencimiento.minusMinutes(minutosAntes);
        Alarma alarma = new Alarma(fechaDisparo, tipo,this.contadorIdAlamas);
        this.alarmas.add(alarma);
        this.contadorIdAlamas++;
    }

    public void agregarAlarma(LocalDateTime fechaDisparo, TipoAlarma tipo){
        Alarma alarma = new Alarma(fechaDisparo, tipo, this.contadorIdAlamas);
        this.alarmas.add(alarma);
        this.contadorIdAlamas++;
    }

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

    public int getId(){
        return this.id;
    }
}
