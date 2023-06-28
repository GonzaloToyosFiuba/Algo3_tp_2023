package GUI;

import javafx.event.ActionEvent;
import Calendario.Calendario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorInicio {
    private Calendario calendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
    public void switchToCalendario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/calendario.fxml"));
        Parent root = loader.load();

        ControladorCalendario controladorCalendario = loader.getController();
        controladorCalendario.setCalendario(calendario);
        controladorCalendario.mostrarInfo();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}