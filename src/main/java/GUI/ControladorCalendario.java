package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import Calendario.*;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorCalendario implements Initializable {
    @FXML
    ScrollPane contenedorTareas;

    private Calendario calendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void mostarInfo(){

        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 6, 4, 20, 56);

        ArrayList<RepresentacionAgendable> agendables = this.calendario.obtenerAgendables(fInicio, fFinal);

        grillaTareas.getChildren().clear();

        int rowIndex = 0;
        for (RepresentacionAgendable agendable : agendables){
            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(40);

            Label titulo = null;
            Label descripcion = null;
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yy"); // YYYY-MM-DD HH:MM:SS

            if (agendable.tipo().equals("Evento")){
                Evento e = calendario.buscarEvento(agendable.id());
                Duration duracion = e.duracionEvento();
                titulo = new Label(e.getTitulo());
                GridPane.setColumnIndex(titulo, 0);
                GridPane.setRowIndex(titulo, rowIndex);
                /* si se ve  pero hay que hay que ver el espacio que ocupa cada columna*/
                descripcion = new Label(e.getDescripcion() + " " + agendable.fecha().format(formato)+ " " + agendable.fecha().plus(duracion).format(formato));
                GridPane.setColumnIndex(descripcion, 1);
                GridPane.setRowIndex(descripcion, rowIndex);
            } else {
                Tarea e = calendario.buscarTarea(agendable.id());
                titulo = new Label(e.getTitulo());
                GridPane.setColumnIndex(titulo, 0);
                GridPane.setRowIndex(titulo, rowIndex);

                descripcion = new Label(e.getDescripcion() + " " + agendable.fecha().format(formato));
                GridPane.setColumnIndex(descripcion, 1);
                GridPane.setRowIndex(descripcion, rowIndex);
            }


            Button b1 = obtenerBotonVer();
            GridPane.setColumnIndex(b1, 4);
            GridPane.setRowIndex(b1, rowIndex);

            Button b2 = obtenerBotonEliminar(agendable, this.calendario);
            GridPane.setColumnIndex(b2, 4);
            GridPane.setRowIndex(b2, rowIndex);
            Insets margin = new Insets(0, 0, 0, 60);
            GridPane.setMargin(b2, margin);

            Button b3 = obtenerBotonEditar();
            GridPane.setColumnIndex(b3, 4);
            GridPane.setRowIndex(b3, rowIndex);
            Insets margin2 = new Insets(0, 0, 0, 120);
            GridPane.setMargin(b3, margin2);

            grillaTareas.getChildren().addAll(titulo, descripcion, b1, b2, b3);

            grillaTareas.getRowConstraints().add(row1);
            rowIndex++;
        }
    }

    Button obtenerBotonVer(){
        Image image = new Image("/ver.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Button b1 = new Button();
        b1.getStyleClass().add("boton-accion-lista");
        b1.setGraphic(imageView);
        return b1;
    }

    Button obtenerBotonEliminar(RepresentacionAgendable agendable, Calendario c){
        Image image = new Image("/tacho.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Button b1 = new Button();
        b1.getStyleClass().add("boton-accion-lista");
        b1.setGraphic(imageView);


        if(agendable.tipo().equals("Evento")){
            b1.setOnAction(event -> {
                System.out.println("Agendable: " + agendable.id());
                c.eliminarEvento(agendable.id());
                this.mostarInfo();
            });
        } else {
            b1.setOnAction(event -> {
                System.out.println("Agendable: " + agendable.id());
                c.eliminarTarea(agendable.id());
                this.mostarInfo();
            });
        }

        return b1;
    }

    Button obtenerBotonEditar(){
        Image image = new Image("/editar.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Button b1 = new Button();
        b1.getStyleClass().add("boton-accion-lista");
        b1.setGraphic(imageView);
        return b1;
    }

    @FXML
    private GridPane grillaTareas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grillaTareas.setPadding(new Insets(10));
        grillaTareas.setHgap(10);
        grillaTareas.setVgap(10);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(35);
    }
}
