package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import Calendario.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorCalendario implements Initializable {
    @FXML
    ScrollPane contenedorTareas;

    private Calendario calendario;

    private enum intervalo{
      DIA,
      SEMANAL,
      MENSUAL
    };

    private  intervalo IntevaloCalendario = intervalo.SEMANAL;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void mostarInfo(){

        LocalDateTime fInicio = LocalDateTime.of(2023, 5, 4, 18, 56);
        LocalDateTime fFinal = LocalDateTime.of(2023, 7, 4, 20, 56);

        ArrayList<RepresentacionAgendable> agendables = intevaloActividades(fInicio);

        grillaTareas.getChildren().clear();

        int rowIndex = 0;
        for (RepresentacionAgendable agendable : agendables){
            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(40);
            Label titulo;
            Label descripcion;
            Label fechaInicio;
            Label fechaFinal;
            Button b1;
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm d/M/yy"); // YYYY-MM-DD HH:MM:SS

            if (agendable.tipo().equals("Evento")){
                Evento e = calendario.buscarEvento(agendable.id());
                Duration duracion = e.duracionEvento();
                titulo = new Label(e.getTitulo());
                GridPane.setColumnIndex(titulo, 0);
                GridPane.setRowIndex(titulo, rowIndex);
                descripcion = new Label(e.getDescripcion());
                fechaInicio = new Label(agendable.fecha().format(formato));
                fechaFinal = new Label(agendable.fecha().plus(duracion).format(formato));
                GridPane.setColumnIndex(descripcion, 1);
                GridPane.setColumnIndex(fechaInicio, 2);
                GridPane.setColumnIndex(fechaFinal, 3);
                GridPane.setRowIndex(descripcion, rowIndex);
                GridPane.setRowIndex(fechaInicio, rowIndex);
                GridPane.setRowIndex(fechaFinal, rowIndex);
                b1 = obtenerBotonVer(e, agendable);
                grillaTareas.getChildren().addAll(fechaFinal);
            } else {
                Tarea e = calendario.buscarTarea(agendable.id());
                titulo = new Label(e.getTitulo());
                GridPane.setColumnIndex(titulo, 0);
                GridPane.setRowIndex(titulo, rowIndex);
                fechaInicio = new Label(agendable.fecha().format(formato));
                GridPane.setColumnIndex(fechaInicio, 2);
                descripcion = new Label(e.getDescripcion());
                GridPane.setColumnIndex(descripcion, 1);
                GridPane.setRowIndex(descripcion, rowIndex);
                GridPane.setRowIndex(fechaInicio, rowIndex);
                b1 = obtenerBotonVer(e, agendable);
            }

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

            grillaTareas.getChildren().addAll(titulo, descripcion, fechaInicio, b1, b2, b3);

            grillaTareas.getRowConstraints().add(row1);
            rowIndex++;
        }
    }

    Button obtenerBotonVer(Agendable agendable, RepresentacionAgendable repre){
        Image image = new Image("/ver.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Button b1 = new Button();
        b1.getStyleClass().add("boton-accion-lista");
        b1.setGraphic(imageView);
        b1.setOnAction(event ->{
            try {
                // Cargar el archivo FXML de la nueva ventana
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaInfo.fxml"));
                Parent root = loader.load();
                //Controlador

                ControladorVentanaInfo controladorVerInfo = loader.getController();
                controladorVerInfo.setAgendable(agendable);
                if (repre.tipo().equals("Tarea")){
                    controladorVerInfo.mostrarInformacionTarea(repre.fecha());
                } else if (repre.tipo().equals("Evento")) {
                    controladorVerInfo.mostrarInformacionEvento(repre.fecha());
                }

                // Crear una nueva escena y mostrarla en una nueva ventana
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
                stage.setScene(scene);
                // Configurar la modalidad de la nueva ventana para bloquear la ventana principal
                stage.initModality(Modality.APPLICATION_MODAL);

                // Mostrar la nueva ventana y bloquear la ejecución del código hasta que se cierre
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    private ArrayList<RepresentacionAgendable> motrarSemana(LocalDateTime fecha){
        LocalDateTime primerDiaSemana = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime ultimoDiaSemana = fecha.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        //System.out.println(primerDiaSemana);
        //System.out.println(ultimoDiaSemana);
        return this.calendario.obtenerAgendables(primerDiaSemana,ultimoDiaSemana);
    }

    private  ArrayList<RepresentacionAgendable> mostrarDia(LocalDateTime fecha){
        return this.calendario.obtenerAgendables(fecha,fecha);
    }

    private ArrayList<RepresentacionAgendable> mostraMes(LocalDateTime fecha){
        LocalDateTime primerDiaMes = fecha.withDayOfMonth(1);
        LocalDateTime ultimoDiaMes = fecha.with(TemporalAdjusters.lastDayOfMonth());
        return this.calendario.obtenerAgendables(primerDiaMes,ultimoDiaMes);
    }

    private ArrayList<RepresentacionAgendable> intevaloActividades(LocalDateTime fecha){
        ArrayList<RepresentacionAgendable> intevalo = null;
        switch (IntevaloCalendario){
            case DIA -> {
                intevalo = mostrarDia(fecha);
                //System.out.println("ACA");
            }
            case SEMANAL -> {
                intevalo = motrarSemana(fecha);
                //System.out.println("ACA");
            }
            case MENSUAL -> {
                intevalo = mostraMes(fecha);
            }
        }
        return intevalo;
    }
}
