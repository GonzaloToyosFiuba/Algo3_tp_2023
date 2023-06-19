package GUI;

import Calendario.Calendario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class ControladorAgregarTarea implements Initializable {
    private Calendario calendario;

    @FXML
    private Button botonAgregar;
    @FXML
    private TextField titulo;
    @FXML
    private TextArea descripcion;
    @FXML
    private Label mensajeError;
    @FXML
    private Spinner<Integer> hora;
    @FXML
    private Spinner<Integer> minutos;
    @FXML
    private DatePicker fecha;
    @FXML
    private CheckBox diaCompleto;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void agregarTarea(){
        if (this.validarEntradas()){
            System.out.println(diaCompleto.isSelected());
            this.calendario.agregarTarea(titulo.getText(),descripcion.getText(),desiganarfecha(),diaCompleto.isSelected());
        }
    }

    private LocalDateTime desiganarfecha(){
        LocalTime tiempo = LocalTime.of(hora.getValue(),minutos.getValue());
        return LocalDateTime.of(this.fecha.getValue(),tiempo);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> rangoHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        hora.setValueFactory(rangoHora);

        SpinnerValueFactory<Integer> rangoMinutos = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minutos.setValueFactory(rangoMinutos);

        fecha.setValue(LocalDate.now());
    }
}
