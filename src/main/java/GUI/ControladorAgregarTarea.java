package GUI;

import Calendario.Calendario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ControladorAgregarTarea{
    private Calendario calendario;

    @FXML
    private Button botonAgregar;
    @FXML
    private TextField titulo;
    @FXML
    private TextArea descripcion;
    @FXML
    private Label mensajeError;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void agregarTarea(){
    this.validarEntradas();
    }

    private boolean validarEntradas(){
        if (titulo.getText().isEmpty()) {
            this.mensajeError.setText("Ingrese un título");
            return false;
        }
        if (descripcion.getText().isEmpty()) {
            this.mensajeError.setText("Ingrese una descripción");
            return false;
        }

        return true;
    }
}
