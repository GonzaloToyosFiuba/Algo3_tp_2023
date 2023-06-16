package GUI;

import javafx.event.ActionEvent;
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
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorCalendario implements Initializable {
    @FXML
    private RadioButton botonDia, botonSemana, botonMes;

    private Calendario calendario;

    private enum Intervalo{
      DIA,
      SEMANAL,
      MENSUAL
    }

    private Intervalo intervaloCalendario;

    private LocalDateTime fechaBaseCalendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void mostrarInfo(){
        ArrayList<RepresentacionAgendable> agendables = obtenerAgendables(this.fechaBaseCalendario);
        grillaTareas.getChildren().clear();

        int rowIndex = 0;
        for (RepresentacionAgendable agendable : agendables){
            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(40);
            Label titulo, descripcion, fechaInicio, fechaFinal;
            Button b1;
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm d/M/yy");

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
                c.eliminarEvento(agendable.id());
                this.mostrarInfo();
            });
        } else {
            b1.setOnAction(event -> {
                c.eliminarTarea(agendable.id());
                this.mostrarInfo();
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

    public void setIntervalo(ActionEvent event){
        this.fechaBaseCalendario = LocalDateTime.now();
        if (botonDia.isSelected()){
            intervaloCalendario = Intervalo.DIA;
        } else if (botonSemana.isSelected()){
            intervaloCalendario = Intervalo.SEMANAL;
        } else {
            intervaloCalendario = Intervalo.MENSUAL;
        }
        this.mostrarInfo();
    }

    public void siguienteIntervalo(){
        if (botonDia.isSelected()){
            this.fechaBaseCalendario = this.fechaBaseCalendario.plusDays(1);
        } else if (botonSemana.isSelected()){
            this.fechaBaseCalendario = this.fechaBaseCalendario.plusWeeks(1);
        } else {
            this.fechaBaseCalendario = this.fechaBaseCalendario.plusMonths(1);
        }
        this.mostrarInfo();
    }

    public void anteriorIntervalo(){
        if (botonDia.isSelected()){
            this.fechaBaseCalendario = this.fechaBaseCalendario.minusDays(1);
        } else if (botonSemana.isSelected()){
            this.fechaBaseCalendario = this.fechaBaseCalendario.minusWeeks(1);
        } else {
            this.fechaBaseCalendario = this.fechaBaseCalendario.minusMonths(1);
        }
        this.mostrarInfo();
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

        ToggleGroup group = new ToggleGroup();
        botonDia.setToggleGroup(group);
        botonMes.setToggleGroup(group);
        botonSemana.setToggleGroup(group);

        this.intervaloCalendario = Intervalo.DIA;
        this.fechaBaseCalendario = LocalDateTime.now();
    }

    private  ArrayList<RepresentacionAgendable> obtenerAgendablesDia(LocalDateTime fecha){
        return this.calendario.obtenerAgendables(fecha.truncatedTo(ChronoUnit.DAYS), fecha.truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1));
    }
    private ArrayList<RepresentacionAgendable> obtenerAgendablesSemana(LocalDateTime fecha){
        LocalDateTime primerDiaSemana = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime ultimoDiaSemana = fecha.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1);
        return this.calendario.obtenerAgendables(primerDiaSemana, ultimoDiaSemana);
    }
    private ArrayList<RepresentacionAgendable> obtenerAgendablesMes(LocalDateTime fecha){
        LocalDateTime primerDiaMes = fecha.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime ultimoDiaMes = fecha.with(TemporalAdjusters.lastDayOfMonth()).truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1);
        return this.calendario.obtenerAgendables(primerDiaMes, ultimoDiaMes);
    }

    private ArrayList<RepresentacionAgendable> obtenerAgendables(LocalDateTime fecha){
        ArrayList<RepresentacionAgendable> intervalo = null;
        switch (this.intervaloCalendario){
            case DIA -> intervalo = obtenerAgendablesDia(fecha);
            case SEMANAL -> intervalo = obtenerAgendablesSemana(fecha);
            case MENSUAL -> intervalo = obtenerAgendablesMes(fecha);
        }
        return intervalo;
    }
}
