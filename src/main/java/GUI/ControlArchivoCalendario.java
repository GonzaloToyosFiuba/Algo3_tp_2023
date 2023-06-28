package GUI;

import Calendario.Calendario;
import Control.AdministradorJSON;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Path;

public class ControlArchivoCalendario {
    private String obtenerDireccion(){
        String ruta = System.getProperty("user.dir");
        return Path.of(ruta,"calendario.json").toString();
    }

    private static final AdministradorJSON admin = new AdministradorJSON();

    public void escribirEnArchivo(Calendario miCalendario) {
        try (Writer writer = new FileWriter(this.obtenerDireccion())) {
            admin.serializar(miCalendario, writer);
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de Archivo");
            alerta.setHeaderText("No se encontro el archivo calendario.json \n" +
                    "No se lo pudo guardar en el calendario.");
            alerta.showAndWait();
        }
    }

    public Calendario leerArchivo() throws IOException {
        File file = new File(this.obtenerDireccion());
        if (!file.exists()) {
            Calendario c = new Calendario();
            file.createNewFile();
            this.escribirEnArchivo(new Calendario());
            return c;
        } else {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            return admin.deserializar(br);
        }
    }
}
