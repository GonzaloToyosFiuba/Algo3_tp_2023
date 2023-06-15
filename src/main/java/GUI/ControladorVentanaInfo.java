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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorVentanaInfo implements Initializable {
    Agendable agendable;
    @FXML
    Label titulo;
    @FXML
    Label descripcion;
    @FXML
    Pane panel;

    @FXML
    private TableView<MuestraAlarma> tablaAlarmas;
    @FXML
    private TableColumn<MuestraAlarma, String> columnaHorario;
    @FXML
    private TableColumn<MuestraAlarma, String> columnaTipo;

    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    public void setAgendable(Agendable agendable){
        this.agendable = agendable;
    }

    public void mostrarInformacionTarea(LocalDateTime fechaRecord){
        Tarea tarea = (Tarea) this.agendable;
        titulo.setText(tarea.getTitulo());
        descripcion.setText(tarea.getDescripcion());
        Label fecha = new Label(fechaRecord.format(formato));
        Text text = new Text("Fecha:");
        text.setLayoutX(20);
        text.setLayoutY(110);
        fecha.setLayoutX(100);
        fecha.setLayoutY(100);

        ObservableList<MuestraAlarma> data = FXCollections.observableArrayList();

        ArrayList<Alarma> alarmas = tarea.obtenerAlarmasOrdenadas();
        for (Alarma alarma : alarmas){
            if (alarma.esRepetible()){
                Duration retroceso = Duration.between(tarea.getFechaVencimiento(), alarma.getHorarioFechaDisparo());
                data.add(new MuestraAlarma(fechaRecord.minus(retroceso).format(formato), alarma.getTipo().toString()));
            } else {
                data.add(new MuestraAlarma(alarma.getHorarioFechaDisparo().format(formato), alarma.getTipo().toString()));
            }
        }
        tablaAlarmas.setItems(data);
        panel.getChildren().addAll(fecha,text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    public static class MuestraAlarma {
        public String horario;
        public String tipo;

        public MuestraAlarma(String horario, String tipo) {
            this.horario = horario;
            this.tipo = tipo;
        }

        public String getHorario() {
            return horario;
        }
        public String getTipo() {
            return tipo;
        }
    }
}
