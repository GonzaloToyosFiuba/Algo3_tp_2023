package GUI;

public class MuestraAlarma {
        public String horario;
        public String tipo;

        public MuestraAlarma(String horario, String tipo) {
            this.horario = horario;
            this.tipo = tipo;
        }

        public String getHorario() {
            return horario;
        }
        public String getTipo() {
            return tipo;
        }
}