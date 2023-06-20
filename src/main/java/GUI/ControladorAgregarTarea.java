package GUI;

import Calendario.Calendario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import Calendario.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControladorAgregarTarea implements Initializable {
    private Calendario calendario;
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
    private final ArrayList<MuestraAlarma> alarmasTabla = new ArrayList<>();

    @FXML
    private TextField titulo;
    @FXML
    private TextArea descripcion;
    @FXML
    private Label mensajeError;
    @FXML
    private Spinner<Integer> hora, minutos , horaAlarmaFija, minutosAlarmaFija, minutosAntes;
    @FXML
    private DatePicker fechaVencimiento, fechaAlarmaFija;
    @FXML
    private CheckBox diaCompleto;
    @FXML
    private RadioButton seleccionAlarmaFija, seleccionAlarmaMinutosAntes;
    @FXML
    private TableView<MuestraAlarma> tablaAlarmas;
    @FXML
    private TableColumn<MuestraAlarma, String> columnaHorario, columnaTipo;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void agregarTarea(){
        if (this.validarEntradas()){
            ArrayList<Alarma> alarmasTarea = new ArrayList<>();

            for (MuestraAlarma alarma : alarmasTabla){
                alarmasTarea.add(new Alarma(LocalDateTime.parse(alarma.getHorario(), formato), TipoAlarma.valueOf(alarma.getTipo())));
            }

            this.calendario.agregarTarea(titulo.getText(), descripcion.getText(),
                                         obtenerFecha(this.fechaVencimiento.getValue(), hora.getValue(), minutos.getValue()),
                                         diaCompleto.isSelected(), alarmasTarea);
        }
    }

    private LocalDateTime obtenerFecha(LocalDate fecha, int hora, int minutos){
        LocalTime tiempo = LocalTime.of(hora, minutos);
        return LocalDateTime.of(fecha, tiempo);
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
        return true;
    }

    public void agregarAlarma(){
        if (seleccionAlarmaFija.isSelected()){
            LocalDateTime fecha = this.obtenerFecha(this.fechaAlarmaFija.getValue(), this.horaAlarmaFija.getValue(), this.minutosAlarmaFija.getValue());
            this.alarmasTabla.add(new MuestraAlarma(fecha.format(formato), TipoAlarma.NOTIFICACION.toString()));
        } else if (seleccionAlarmaMinutosAntes.isSelected()){
            LocalDateTime fecha = this.obtenerFecha(this.fechaVencimiento.getValue(), this.horaAlarmaFija.getValue(), this.minutosAlarmaFija.getValue());
            this.alarmasTabla.add(new MuestraAlarma(fecha.minusMinutes(this.minutosAntes.getValue()).format(formato), TipoAlarma.NOTIFICACION.toString()));
        }
        ObservableList<MuestraAlarma> data = FXCollections.observableArrayList();
        this.cargarAlarmas(data);
        tablaAlarmas.setItems(data);
    }

    private void cargarAlarmas(ObservableList<MuestraAlarma> data){
        this.alarmasTabla.forEach(data::add);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hora.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        horaAlarmaFija.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));

        minutos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        minutosAlarmaFija.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        minutosAntes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        fechaVencimiento.setValue(LocalDate.now());
        fechaAlarmaFija.setValue(LocalDate.now());

        ToggleGroup group = new ToggleGroup();
        seleccionAlarmaFija.setToggleGroup(group);
        seleccionAlarmaMinutosAntes.setToggleGroup(group);

        columnaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
}
