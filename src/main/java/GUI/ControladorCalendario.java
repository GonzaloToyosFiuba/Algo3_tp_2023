package GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import Calendario.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorCalendario implements Initializable {
    @FXML
    private RadioButton botonDia, botonSemana, botonMes;
    @FXML
    private Label infoIntervaloMostrado;
    private GestorAlarmas gestorAlarmas;
    private static final DateTimeFormatter formatoLabelIntervalo = DateTimeFormatter.ofPattern("dd/MM/yy");
    private Calendario calendario;
    private final ControlArchivoCalendario escritura = new ControlArchivoCalendario();

    private enum Intervalo{
      DIA,
      SEMANAL,
      MENSUAL
    }

    private Intervalo intervaloCalendario;

    private LocalDateTime fechaBaseCalendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
        this.gestorAlarmas = new GestorAlarmas(this.calendario);
        this.gestorAlarmas.inicializar();
    }

    public void mostrarInfo(){
        ArrayList<RepresentacionAgendable> agendables = obtenerAgendables(this.fechaBaseCalendario);
        grillaTareas.getChildren().clear();

        int rowIndex = 0;
        for (RepresentacionAgendable agendable : agendables){
            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(40);

            VisitorMostrarCalendario visitor = new VisitorMostrarCalendario(this.calendario, this.grillaTareas, rowIndex, agendable.fecha(), this);
            agendable.agendable().aceptar(visitor);

            grillaTareas.getRowConstraints().add(row1);
            rowIndex++;
        }

        this.escritura.escribirEnArchivo(this.calendario);
    }
    public void setIntervalo(){
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

    @FXML
    private ChoiceBox<String> selectorAgregar;

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

        String[] opciones = {"Agregar", "Nueva tarea","Nuevo evento"};
        this.selectorAgregar.getItems().addAll(opciones);
        this.selectorAgregar.setOnAction(this::agregarNuevoAgendable);

        this.selectorAgregar.setValue("Agregar");

        Platform.runLater( () -> grillaTareas.getScene().getWindow().setOnCloseRequest(windowEvent -> this.gestorAlarmas.detener()) );
    }

    private void agregarNuevoAgendable(ActionEvent event){
        String seleccion = selectorAgregar.getValue();

        if (seleccion.equals("Nueva tarea")){
            this.abrirVentanaAgregarTarea();
        } else if (seleccion.equals("Nuevo evento")){
            this.abrirVentanaAgregarEvento();
        }
        this.selectorAgregar.setValue("Agregar");
    }

    private void abrirVentanaAgregarTarea(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/agregarTarea.fxml"));
            Parent root = loader.load();

            ControladorAgregarTarea controladorAgregarTarea = loader.getController();
            controladorAgregarTarea.setCalendario(this.calendario);
            this.abrirNuevaVentana(root);
            this.mostrarInfo();
        } catch (IOException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de Archivo");
            alerta.setHeaderText("Error al cargar el archivo");
            alerta.showAndWait();
        }
    }

    private void abrirVentanaAgregarEvento(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/agregarEvento.fxml"));
            Parent root = loader.load();

            ControladorAgregarEvento controladorAgregarEvento = loader.getController();
            controladorAgregarEvento.setCalendario(this.calendario);
            this.abrirNuevaVentana(root);
            this.mostrarInfo();
        } catch (IOException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de Archivo");
            alerta.setHeaderText("Error al cargar el archivo");
            alerta.showAndWait();
        }
    }

    private void abrirNuevaVentana(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private ArrayList<RepresentacionAgendable> obtenerAgendablesDia(LocalDateTime fecha){
        this.infoIntervaloMostrado.setText("Viendo el " + fecha.truncatedTo(ChronoUnit.DAYS).format(formatoLabelIntervalo));
        return this.calendario.obtenerAgendables(fecha.truncatedTo(ChronoUnit.DAYS), fecha.truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1));
    }
    private ArrayList<RepresentacionAgendable> obtenerAgendablesSemana(LocalDateTime fecha){
        LocalDateTime primerDiaSemana = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime ultimoDiaSemana = fecha.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1);
        this.infoIntervaloMostrado.setText("Viendo desde " + primerDiaSemana.format(formatoLabelIntervalo) + " hasta " + ultimoDiaSemana.format(formatoLabelIntervalo));
        return this.calendario.obtenerAgendables(primerDiaSemana, ultimoDiaSemana);
    }
    private ArrayList<RepresentacionAgendable> obtenerAgendablesMes(LocalDateTime fecha){
        LocalDateTime primerDiaMes = fecha.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime ultimoDiaMes = fecha.with(TemporalAdjusters.lastDayOfMonth()).truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1);
        this.infoIntervaloMostrado.setText("Viendo desde " + primerDiaMes.format(formatoLabelIntervalo) + " hasta " + ultimoDiaMes.format(formatoLabelIntervalo));
        return this.calendario.obtenerAgendables(primerDiaMes, ultimoDiaMes);
    }

    private ArrayList<RepresentacionAgendable> obtenerAgendables(LocalDateTime fecha){
        ArrayList<RepresentacionAgendable> agendables = null;
        switch (this.intervaloCalendario){
            case DIA -> agendables = obtenerAgendablesDia(fecha);
            case SEMANAL -> agendables = obtenerAgendablesSemana(fecha);
            case MENSUAL -> agendables = obtenerAgendablesMes(fecha);
        }
        return agendables;
    }
}
