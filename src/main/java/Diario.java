import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Diario extends Evento{
    int intervaloDeDias;

    public Diario(int cantidadRepeticiones, String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinal, LocalDateTime fechaLimite, int id, int inter) {
        super(cantidadRepeticiones, titulo, descripcion, fechaInicio, fechaFinal, fechaLimite, id);
        this.intervaloDeDias = inter;
    }

    @Override
    public ArrayList<LocalDateTime> obtenerRepeticionesEntre(LocalDateTime f1, LocalDateTime f2) {
        ArrayList<LocalDateTime> fechas = new ArrayList<LocalDateTime>();
        LocalDateTime aux_fInicial = super.fechaInicio;
        LocalDateTime aux_fFinal = super.fechaFinal;
        Duration duracion = Duration.between(super.fechaInicio, super.fechaFinal);
        /*for (LocalDateTime aux = f1; aux.compareTo(f2) <= 0 ; aux = aux.plusDays(1)){
                if (aux_fInicial.compareTo(aux) == 0 || aux_fFinal.compareTo(aux) == 0){

                }
                aux_fInicial = aux_fInicial.plusDays(intervaloDeDias);
                aux_fFinal = aux_fFinal.plusDays(intervaloDeDias);
        }*/
        /* lo primero que se hace se ve la primera fecha que seria la original del evento
            si esta entre f1 y f2 se lo agrega a la lista.
            Chequear la fecha limite y la cantidad de repeticiones
            Si todabia quedan repeticiones o no se llego a la fecha limite que siga
        */
        //  finicial 12      16
        for (int i = 0; i < super.cantidadRepeticiones ; i++){ // -> FALTA TEST !!!!!!!!!e

            if (i > 0){
                aux_fInicial = aux_fFinal.plusDays(intervaloDeDias * i).withHour(super.fechaInicio.getHour());
                aux_fFinal = aux_fInicial.plus(duracion);
            }

            if (f1.compareTo(aux_fInicial) <= 0 ){
                fechas.add(aux_fInicial);
            }//&& f2.compareTo(aux_fFinal) >= 0
        }
        return fechas;

    }
    public static void main(String[] args) {
        LocalDateTime finicio = LocalDateTime.of(2023,4,16,18,38);
        LocalDateTime ffinal =LocalDateTime.of(2023,4,19,18,38);
        LocalDateTime f1 = LocalDateTime.of(2023,4,5,0,0);
        LocalDateTime f2 = LocalDateTime.of(2023,7,22,00,00);
        Evento y = new Diario(2,"pepe","hola",finicio,ffinal,ffinal,50,1);
        ArrayList<LocalDateTime> fechitas = y.obtenerRepeticionesEntre(f1,f2);

        System.out.println(fechitas.size());
    }

}

