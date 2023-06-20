package GUI;

import Calendario.Calendario;
import Frecuencias.Anual;
import Frecuencias.Diaria;
import Frecuencias.Mensual;
import Frecuencias.TipoFrecuencia;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorAgregarEvento implements Initializable {
    @FXML
    private TextField titulo;
    @FXML
    private TextArea descripcion;
    @FXML
    private Label mensajeError;
    @FXML
    private Spinner<Integer> horaInicioEvento, minutosInicioEvento, horaFinalEvento, minutosFinalEvento;
    @FXML
    private Spinner<Integer> intervalo, repeticionesMaximas, horaAlarmaFija, minutosAlarmaFija, minutosAntes;
    @FXML
    private DatePicker fechaInicioEvento, fechaFinalEvento, fechaLimite, fechaAlarmaFija;
    @FXML
    private RadioButton seleccionAlarmaFija, seleccionAlarmaMinutosAntes;
    @FXML
    private RadioButton seleccionCantidadMax, seleccionFechaLimite, seleccionInfinito;
    @FXML
    private ChoiceBox<String> selectorTipoFrecuencia;
    @FXML
    private Button botonAgregar;
    @FXML
    private CheckBox diaCompleto;
    private Calendario calendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void agregarEvento(){
        if (validarEntradas()){
            LocalDateTime fechaInicio = obtenerFecha(fechaInicioEvento.getValue(), horaInicioEvento.getValue(), minutosInicioEvento.getValue());
            LocalDateTime fechaFinal = obtenerFecha(fechaFinalEvento.getValue(), horaFinalEvento.getValue(), minutosFinalEvento.getValue());
            if(seleccionCantidadMax.isSelected()){
                calendario.agregarEventoCantMax(descripcion.getText(), titulo.getText(),
                                                fechaInicio, fechaFinal, repeticionesMaximas.getValue(), definirRepeticion(), diaCompleto.isSelected());
            } else if (seleccionFechaLimite.isSelected()){
                LocalDateTime fechaLimite = obtenerFecha(this.fechaLimite.getValue(), 23, 59);
                calendario.agregarEventoFechaLimite(descripcion.getText(), titulo.getText(),
                                                    fechaInicio, fechaFinal, fechaLimite, definirRepeticion(), diaCompleto.isSelected());
            } else if (seleccionInfinito.isSelected()){
                LocalDateTime fechaLimite = LocalDateTime.MAX;
                calendario.agregarEventoFechaLimite(descripcion.getText(), titulo.getText(),
                                                    fechaInicio, fechaFinal, fechaLimite, definirRepeticion(), diaCompleto.isSelected());
            }
            Stage stage = (Stage) botonAgregar.getScene().getWindow();
            stage.close();
        }
    }

    private boolean validarEntradas(){
        if (titulo.getText().isBlank()) {
            this.mensajeError.setText("Ingrese un título");
            return false;
        }
        if (descripcion.getText().isBlank()) {
            this.mensajeError.setText("Ingrese una descripción");
            return false;
        }

        LocalDateTime fechaFinal = obtenerFecha(fechaFinalEvento.getValue(), horaFinalEvento.getValue(), minutosFinalEvento.getValue());
        LocalDateTime fechaInicio = obtenerFecha(fechaInicioEvento.getValue(), horaInicioEvento.getValue(), minutosInicioEvento.getValue());
        if(!fechaInicio.isBefore(fechaFinal)){
            this.mensajeError.setText("La fecha final debe ser posterior a la fecha inicial");
            return false;
        }

        LocalDateTime fechaLimite = obtenerFecha(this.fechaLimite.getValue(), 23, 59);
        if(seleccionFechaLimite.isSelected() && !fechaFinal.isBefore(fechaLimite)){
            this.mensajeError.setText("La fecha límite debe ser posterior a la fecha final");
            return false;
        }
        return true;
    }

    private TipoFrecuencia definirRepeticion(){
        String seleccion = selectorTipoFrecuencia.getValue();
        if (seleccion.equals("Diario")){
            return new Diaria(intervalo.getValue());
        } else if (seleccion.equals("Mensual")){
            return new Mensual(intervalo.getValue());
        } else {//anual
            return new Anual(intervalo.getValue());
        }
    }

    public void agregarAlarma(){
    }

    private LocalDateTime obtenerFecha(LocalDate fecha, int hora, int minutos){
        LocalTime tiempo = LocalTime.of(hora, minutos);
        return LocalDateTime.of(fecha, tiempo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        horaAlarmaFija.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        horaFinalEvento.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        horaInicioEvento.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));


        minutosAlarmaFija.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        minutosFinalEvento.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        minutosInicioEvento.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        minutosAntes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        intervalo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1));
        repeticionesMaximas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));

        fechaFinalEvento.setValue(LocalDate.now());
        fechaInicioEvento.setValue(LocalDate.now());
        fechaLimite.setValue(LocalDate.now());
        fechaAlarmaFija.setValue(LocalDate.now());

        ToggleGroup grupoAlarmas = new ToggleGroup();
        seleccionAlarmaFija.setToggleGroup(grupoAlarmas);
        seleccionAlarmaMinutosAntes.setToggleGroup(grupoAlarmas);

        ToggleGroup grupoLimite = new ToggleGroup();
        seleccionCantidadMax.setToggleGroup(grupoLimite);
        seleccionFechaLimite.setToggleGroup(grupoLimite);
        seleccionInfinito.setToggleGroup(grupoLimite);

        String[] opciones = {"Diario", "Mensual", "Anual"};
        this.selectorTipoFrecuencia.getItems().addAll(opciones);

        this.selectorTipoFrecuencia.setValue("Diario");
    }
}
