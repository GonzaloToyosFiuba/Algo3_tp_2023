package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Calendario.*;

import java.io.*;
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private final ControlArchivoCalendario controlArchivo = new ControlArchivoCalendario();

    @Override
    public void start(Stage stage) {
        FXMLLoader loader;
        Parent root;
        Calendario c;
        loader = new FXMLLoader(getClass().getResource("/inicio.fxml"));
        try {
            root = loader.load();
            c = controlArchivo.leerArchivo();
        } catch (IOException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de Archivo");
            alerta.setHeaderText("No se pudo leer el archivo calendario.json");
            alerta.showAndWait();
            return;
        }
        ControladorInicio controller = loader.getController();

        Image icon = new Image("icono_calendario.png");
        stage.getIcons().add(icon);

        controller.setCalendario(c);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        stage.setTitle("Calendario");
        stage.setScene(scene);
        stage.show();
    }
}
