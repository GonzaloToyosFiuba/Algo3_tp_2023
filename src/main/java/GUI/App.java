package GUI;

import Control.AdministradorJSON;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Calendario.*;

import java.io.*;
import java.util.ArrayList;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inicio.fxml"));
            Parent root = loader.load();

            ControladorInicio controller = loader.getController();

            Image icon = new Image("icono_calendario.png");
            stage.getIcons().add(icon);

            Calendario c = recibirInformacion();

            controller.setCalendario(c);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
            stage.setTitle("Calendario");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Calendario recibirInformacion() throws Exception {
        String actual = new File("").getAbsolutePath();
        String dir = actual + "\\calendario.json";

        File file = new File(dir);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            AdministradorJSON admin = new AdministradorJSON();
            return admin.deserializar(br);
        }

        return new Calendario();
    }
}
