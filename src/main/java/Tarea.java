import java.time.LocalDateTime;
import java.util.ArrayList;

public class Tarea {
    private int id;
    private boolean completado;
    private LocalDateTime fechaVencimiento;
    private ArrayList<Alarma> alarmas;
    private boolean diaCompleto;

    public Tarea(LocalDateTime fechaVencimiento, boolean diaCompleto) {
        this.fechaVencimiento = fechaVencimiento;
        this.diaCompleto = diaCompleto;
        this.alarmas = new ArrayList<Alarma>();
    }
    public void agregarAlarma(Alarma alarma){
        this.alarmas.add(alarma);
    }

    public Alarma obtenerUltimaAlarma(){
        return alarmas.get(alarmas.size()-1);
    }
    /*
    establecerAlarma : definimos fecha de la alarma junto con su tipo de alarma
     */

    /*
    establecerAlarma : pide un localdatetime tiempo anterior a la fechaVencimiento, se dice el tipo de alarma
     */
}
