package GUI;

import Calendario.Calendario;
import Calendario.Evento;
import Calendario.Tarea;
import Calendario.Agendable;
import Calendario.VisitorAgendable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitorMostrarCalendario implements VisitorAgendable {
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
    private Calendario calendario;
    private GridPane grilla;
    private int rowIndex;
    private final LocalDateTime fecha;
    private ControladorCalendario controlador;
    private final ControlArchivoCalendario escritura = new ControlArchivoCalendario();

    public VisitorMostrarCalendario(Calendario calendario, GridPane grilla, int rowIndex, LocalDateTime fecha, ControladorCalendario controlador) {
        this.calendario = calendario;
        this.grilla = grilla;
        this.rowIndex = rowIndex;
        this.fecha = fecha;
        this.controlador = controlador;
    }

    @Override
    public void visitarEvento(Evento evento) {
        Pane fondo = new Pane();
        Duration duracion = evento.duracionEvento();
        Label titulo = new Label(evento.getTitulo());
        GridPane.setColumnIndex(titulo, 0);
        GridPane.setRowIndex(titulo, rowIndex);
        Label descripcion = new Label(evento.getDescripcion());
        Label fechaInicio = new Label(fecha.format(formato));
        Label fechaFinal = new Label(fecha.plus(duracion).format(formato));
        GridPane.setColumnIndex(descripcion, 1);
        GridPane.setColumnIndex(fechaInicio, 2);
        GridPane.setColumnIndex(fechaFinal, 3);
        GridPane.setRowIndex(descripcion, rowIndex);
        GridPane.setRowIndex(fechaInicio, rowIndex);
        GridPane.setRowIndex(fechaFinal, rowIndex);
        Button b1 = obtenerBotonVer(evento, true);
        GridPane.setColumnIndex(b1, 4);
        GridPane.setRowIndex(b1, rowIndex);
        GridPane.setColumnIndex(fondo,0);
        GridPane.setColumnSpan(fondo, Integer.MAX_VALUE);
        GridPane.setRowIndex(fondo, rowIndex);
        fondo.setStyle("-fx-background-color: rgba(0, 72, 255, 0.57);");

        Button b2 = obtenerBotonEliminar(evento, true);
        GridPane.setColumnIndex(b2, 4);
        GridPane.setRowIndex(b2, rowIndex);
        Button b3 = obtenerBotonEditar();
        GridPane.setColumnIndex(b3, 4);
        GridPane.setRowIndex(b3, rowIndex);
        Insets margin = new Insets(0, 0, 0, 60);
        GridPane.setMargin(b2, margin);
        Insets margin3 = new Insets(0, 0, 0, 120);
        GridPane.setMargin(b3, margin3);
        this.grilla.getChildren().addAll(titulo, descripcion, fechaInicio, fondo, fechaFinal, b1, b2, b3);
    }

    @Override
    public void visitarTarea(Tarea tarea) {
        Pane fondo = new Pane();
        Label titulo = new Label(tarea.getTitulo());
        GridPane.setColumnIndex(titulo, 0);
        GridPane.setRowIndex(titulo, rowIndex);
        Label fechaInicio = new Label(this.fecha.format(formato));
        GridPane.setColumnIndex(fechaInicio, 2);
        Label descripcion = new Label(tarea.getDescripcion());
        GridPane.setColumnIndex(descripcion, 1);
        GridPane.setRowIndex(descripcion, rowIndex);
        GridPane.setRowIndex(fechaInicio, rowIndex);
        GridPane.setColumnIndex(fondo,0);
        GridPane.setColumnSpan(fondo, Integer.MAX_VALUE);
        GridPane.setRowIndex(fondo, rowIndex);
        fondo.setStyle("-fx-background-color: rgba(185,97,250,0.57);");
        Button b1 = obtenerBotonVer(tarea, false);
        GridPane.setColumnIndex(b1, 4);
        GridPane.setRowIndex(b1, rowIndex);

        Button b2 = obtenerBotonEliminar(tarea, false);
        GridPane.setColumnIndex(b2, 4);
        GridPane.setRowIndex(b2, rowIndex);
        Insets margin = new Insets(0, 0, 0, 60);
        GridPane.setMargin(b2, margin);
        CheckBox completada = new CheckBox();
        completada.setSelected(tarea.estaCompleta());
        completada.setOnAction(event -> {
            tarea.setCompletada(completada.isSelected());
            this.escritura.escribirEnArchivo(this.calendario);
        });
        GridPane.setColumnIndex(completada, 5);
        GridPane.setRowIndex(completada, rowIndex);
        grilla.getChildren().addAll(fondo);
        if (tarea.esDiaCompleto()){
            Label fechaFinal = new Label("Día completo");
            GridPane.setColumnIndex(fechaFinal, 3);
            GridPane.setRowIndex(fechaFinal, rowIndex);
            grilla.getChildren().addAll(fechaFinal);
        }
        Button b3 = obtenerBotonEditar();
        GridPane.setColumnIndex(b3, 4);
        GridPane.setRowIndex(b3, rowIndex);
        Insets margin3 = new Insets(0, 0, 0, 120);
        GridPane.setMargin(b3, margin3);
        this.grilla.getChildren().addAll(titulo, descripcion, fechaInicio, b1, b2, b3, completada);
    }

    Button obtenerBotonVer(Agendable agendable, Boolean esEvento){
        Button b1 = obtenerBotonConImagen(new Image("/ver.png"));

        b1.setOnAction(event ->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaInfo.fxml"));
                Parent root = loader.load();

                ControladorVentanaInfo controladorVerInfo = loader.getController();
                controladorVerInfo.setAgendable(agendable);
                if (esEvento){
                    controladorVerInfo.mostrarInformacionEvento(this.fecha);
                } else{
                    controladorVerInfo.mostrarInformacionTarea(this.fecha);
                }
                this.abrirNuevaVentana(root);
            } catch (IllegalStateException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Archivo");
                alerta.setHeaderText("No se encontro ventanaInfo.fxml");
                alerta.showAndWait();
            } catch (IOException e){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Archivo");
                alerta.setHeaderText("Error al cargar el archivo");
                alerta.showAndWait();
            }
        });
        return b1;
    }

    Button obtenerBotonEliminar(Agendable agendable, Boolean esEvento){
        Button b1 = obtenerBotonConImagen(new Image("/tacho.png"));

        if(esEvento){
            b1.setOnAction(event -> {
                this.calendario.eliminarEvento(agendable.getId());
                this.controlador.mostrarInfo();
            });
        } else {
            b1.setOnAction(event -> {
                this.calendario.eliminarTarea(agendable.getId());
                this.controlador.mostrarInfo();
            });
        }

        return b1;
    }

    Button obtenerBotonEditar(){
        return obtenerBotonConImagen(new Image("/editar.png"));
    }

    Button obtenerBotonConImagen(Image imagen){
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Button boton = new Button();
        boton.getStyleClass().add("boton-accion-lista");
        boton.setGraphic(imageView);
        return boton;
    }
    private void abrirNuevaVentana(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
