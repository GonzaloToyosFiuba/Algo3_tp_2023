package GUI;

import Calendario.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorVentanaInfo implements Initializable {
    @FXML
    VBox contenedorInfo;
    @FXML
    private TableView<MuestraAlarma> tablaAlarmas;
    @FXML
    private TableColumn<MuestraAlarma, String> columnaHorario;
    @FXML
    private TableColumn<MuestraAlarma, String> columnaTipo;

    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    public void mostrarInformacionTarea(Tarea tarea, LocalDateTime fechaRecord){
        contenedorInfo.setSpacing(10);
        agregarLabel("Título: " + tarea.getTitulo(), "estilo-vacio");
        agregarLabel("Descripción: " + tarea.getDescripcion(), "label-descripcion");
        agregarLabel("Fecha: " + fechaRecord.format(formato), "estilo-vacio");

        ObservableList<MuestraAlarma> data = FXCollections.observableArrayList();
        ArrayList<Alarma> alarmas = tarea.obtenerAlarmasOrdenadas();

        cargarAlarmas(alarmas, data, tarea.getFechaVencimiento(), fechaRecord);

        tablaAlarmas.setItems(data);
    }

    private void agregarLabel(String text, String claseEstilo) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(claseEstilo);
        contenedorInfo.getChildren().add(label);
    }

    public void mostrarInformacionEvento(Evento evento, LocalDateTime fechaRecord){
        contenedorInfo.setSpacing(10);
        agregarLabel("Título: " + evento.getTitulo(), "estilo-vacio");
        agregarLabel("Descripción: " + evento.getDescripcion(), "estilo-vacio");
        agregarLabel("Fecha inicio: " + fechaRecord.format(formato), "estilo-vacio");
        agregarLabel("Fecha final: " + fechaRecord.plus(evento.duracionEvento()).format(formato), "estilo-vacio");
        agregarLabel("Repeticion: " + evento.getTipoFrecuencia().toString(), "estilo-vacio");

        ArrayList<Alarma> alarmas = evento.obtenerAlarmasOrdenadas();

        ObservableList<MuestraAlarma> data = FXCollections.observableArrayList();
        cargarAlarmas(alarmas, data, evento.getFechaInicio(), fechaRecord);
        tablaAlarmas.setItems(data);
    }

    private void cargarAlarmas(ArrayList<Alarma> alarmas, ObservableList<MuestraAlarma> data, LocalDateTime fechaOriginal, LocalDateTime fechaRecord){
        for (Alarma alarma : alarmas){
            if (alarma.esRepetible()){
                Duration retroceso = Duration.between(alarma.getHorarioFechaDisparo(),fechaOriginal);
                data.add(new MuestraAlarma(fechaRecord.minus(retroceso).format(formato), alarma.getTipo().toString()));
            } else {
                if(fechaOriginal.compareTo(fechaRecord) == 0) {
                    data.add(new MuestraAlarma(alarma.getHorarioFechaDisparo().format(formato), alarma.getTipo().toString()));
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
}
