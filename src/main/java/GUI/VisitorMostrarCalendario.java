package GUI;

import Calendario.Calendario;
import Calendario.Evento;
import Calendario.Tarea;
import Calendario.Agendable;
import Calendario.VisitorAgendable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VisitorMostrarCalendario implements VisitorAgendable {
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
    private final Calendario calendario;
    private final GridPane grilla;
    private final int rowIndex;
    private final LocalDateTime fecha;
    private final ControladorCalendario controlador;
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
        this.agregarFondo(this.grilla, rowIndex, 0, "-fx-background-color: rgba(0, 72, 250, 0.57);");

        this.agregarEnGrilla(this.grilla, rowIndex, 0, new Label(evento.getTitulo()));
        this.agregarEnGrilla(this.grilla, rowIndex, 1, new Label(evento.getDescripcion()));
        this.agregarEnGrilla(this.grilla, rowIndex, 2, new Label(fecha.format(formato)));
        this.agregarEnGrilla(this.grilla, rowIndex, 3, new Label(fecha.plus(evento.duracionEvento()).format(formato)));
        this.agregarEnGrilla(this.grilla, rowIndex, 4, obtenerBotonVer(evento));

        Button b2 = obtenerBotonEliminar(evento);
        this.agregarEnGrilla(this.grilla, rowIndex, 4, b2);
        GridPane.setMargin(b2, new Insets(0, 0, 0, 60));

        Button b3 = obtenerBotonEditar();
        this.agregarEnGrilla(this.grilla, rowIndex, 4, b3);
        GridPane.setMargin(b3, new Insets(0, 0, 0, 120));
    }

    @Override
    public void visitarTarea(Tarea tarea) {
        this.agregarFondo(this.grilla, rowIndex, 0, "-fx-background-color: rgba(185, 97, 250, 0.57);");

        this.agregarEnGrilla(this.grilla, rowIndex, 0, new Label(tarea.getTitulo()));
        this.agregarEnGrilla(this.grilla, rowIndex, 1, new Label(tarea.getDescripcion()));
        this.agregarEnGrilla(this.grilla, rowIndex, 2, new Label(this.fecha.format(formato)));
        this.agregarEnGrilla(this.grilla, rowIndex, 4, obtenerBotonVer(tarea));

        if (tarea.esDiaCompleto()){
            this.agregarEnGrilla(this.grilla, rowIndex, 3, new Label("Día completo"));
        }

        Button b2 = obtenerBotonEliminar(tarea);
        this.agregarEnGrilla(this.grilla, rowIndex, 4, b2);
        GridPane.setMargin(b2, new Insets(0, 0, 0, 60));

        Button b3 = obtenerBotonEditar();
        this.agregarEnGrilla(this.grilla, rowIndex, 4, b3);
        GridPane.setMargin(b3, new Insets(0, 0, 0, 120));

        CheckBox completada = new CheckBox();
        completada.setSelected(tarea.estaCompleta());
        completada.setOnAction(event -> {
            tarea.setCompletada(completada.isSelected());
            this.escritura.escribirEnArchivo(this.calendario);
        });
        this.agregarEnGrilla(this.grilla, rowIndex, 5, completada);
    }

    private void agregarEnGrilla(GridPane grilla, int fila, int columna, Node elemento){
        GridPane.setColumnIndex(elemento,  columna);
        GridPane.setRowIndex(elemento, fila);
        grilla.getChildren().add(elemento);
    }

    private void agregarFondo(GridPane grilla, int fila, int columna, String estiloCss){
        Pane fondo = new Pane();
        GridPane.setColumnSpan(fondo, Integer.MAX_VALUE);
        fondo.setStyle(estiloCss);
        this.agregarEnGrilla(grilla, fila, columna, fondo);
    }
    Button obtenerBotonVer(Agendable agendable){
        Button b1 = obtenerBotonConImagen(new Image("/ver.png"));

        b1.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanaInfo.fxml"));
                Parent root = loader.load();

                agendable.aceptar(new VisitorVerAgendable(loader.getController(), this.fecha));

                this.abrirNuevaVentana(root);
            } catch (IOException e){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Archivo");
                alerta.setHeaderText("Error al cargar el archivo");
                alerta.showAndWait();
            }
        });

        return b1;
    }

    Button obtenerBotonEliminar(Agendable agendable){
        Button b1 = obtenerBotonConImagen(new Image("/tacho.png"));

        b1.setOnAction(event -> {
            agendable.aceptar(new VisitorEliminarAgendable(this.calendario));
            this.controlador.mostrarInfo();
        });

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
