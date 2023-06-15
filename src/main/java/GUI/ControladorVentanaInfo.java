package GUI;

import Calendario.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ControladorVentanaInfo implements Initializable {
    Agendable agendable;
    @FXML
    GridPane grillaAlarmas;
    @FXML
    Label titulo;
    @FXML
    Label descripcion;
    @FXML
    Pane panel;

    public void setAgendable(Agendable agendable){
        this.agendable = agendable;
    }

    public void mostrarInformacionTarea(LocalDateTime fechaVen){
        Tarea tarea = (Tarea) this.agendable;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yy");
        titulo.setText(tarea.getTitulo());
        descripcion.setText(tarea.getDescripcion());
        Label fecha = new Label(fechaVen.format(formato));
        Text text = new Text("Fecha:");
        text.setLayoutX(20);
        text.setLayoutY(110);
        fecha.setLayoutX(100);
        fecha.setLayoutY(100);
        panel.getChildren().addAll(fecha,text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grillaAlarmas.setPadding(new Insets(10));
        grillaAlarmas.setHgap(10);
        grillaAlarmas.setVgap(10);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(35);
    }
}
