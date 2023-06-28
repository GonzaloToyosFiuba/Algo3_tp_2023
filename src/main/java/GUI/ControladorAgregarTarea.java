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
import javafx.stage.Stage;

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
    private Spinner<Integer> hora, minutos, horaAlarmaFija, minutosAlarmaFija, minutosAntes;
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
    @FXML
    private Button botonAgregar;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void agregarTarea(){
        if (this.validarEntradas()){
            ArrayList<Alarma> alarmasTarea = new ArrayList<>();

            for (MuestraAlarma alarma : alarmasTabla){
                if(alarma.esMinutosAntes){
                    LocalDateTime fecha = obtenerFecha(this.fechaVencimiento.getValue(), hora.getValue(), minutos.getValue()).minusMinutes(alarma.minutosAntes);
                    alarmasTarea.add(new Alarma(fecha, TipoAlarma.valueOf(alarma.getTipo()), 0));
                } else {
                    alarmasTarea.add(new Alarma(LocalDateTime.parse(alarma.getHorario(), formato), TipoAlarma.valueOf(alarma.getTipo()), 0));
                }
            }

            this.calendario.agregarTarea(titulo.getText(), descripcion.getText(),
                                         obtenerFecha(this.fechaVencimiento.getValue(), hora.getValue(), minutos.getValue()),
                                         diaCompleto.isSelected(), alarmasTarea);


            Stage stage = (Stage) botonAgregar.getScene().getWindow();
            stage.close();
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
        AdministradorAlarmas admin = new AdministradorAlarmas();
        LocalDateTime fecha = this.obtenerFecha(this.fechaAlarmaFija.getValue(), this.horaAlarmaFija.getValue(), this.minutosAlarmaFija.getValue());
        admin.agregarAlarmaTabla(seleccionAlarmaFija.isSelected(), this.alarmasTabla, this.tablaAlarmas, fecha, this.minutosAntes.getValue());
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
