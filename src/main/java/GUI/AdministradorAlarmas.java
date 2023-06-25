package GUI;

import Calendario.TipoAlarma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AdministradorAlarmas {
    public void agregarAlarmaTabla(boolean seleccionAlarmaFija, ArrayList<MuestraAlarma> alarmasTabla , TableView<MuestraAlarma> tablaAlarmas, LocalDateTime fecha, Integer minutosAntes){
        if (seleccionAlarmaFija){
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
            alarmasTabla.add(new MuestraAlarma(fecha.format(formato), TipoAlarma.NOTIFICACION.toString()));
        } else {
            alarmasTabla.add(new MuestraAlarma(minutosAntes.toString() + " minutos antes", TipoAlarma.NOTIFICACION.toString(), minutosAntes));
        }
        ObservableList<MuestraAlarma> data = FXCollections.observableArrayList();
        data.addAll(alarmasTabla);
        tablaAlarmas.setItems(data);
    }
}
