package GUI;

import Calendario.Alarma;
import Calendario.Calendario;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GestorAlarmas {
    private Calendario calendario;

    GestorAlarmas (Calendario calendario){
        this.calendario = calendario;
    }

    public void inicializar() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LocalDateTime horaActual = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                ArrayList<Alarma> alarmasActuales = calendario.obtenerAlarmas(horaActual);

                for (Alarma alarma : alarmasActuales) {
                    if (horaActual.equals(alarma.getHorarioFechaDisparo())) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Alarma");
                            alert.setHeaderText("Alarma sonando");
                            alert.setContentText("¡Tenés una actividad programada!\n\n" + alarma.getMensaje());
                            alert.show();
                        });
                    }
                }
            }
        };

        long delay = 60_000 - System.currentTimeMillis() % 60_000;
        timer.schedule(task, delay, 60 * 1000);
    }
}
