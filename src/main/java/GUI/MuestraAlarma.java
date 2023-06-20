package GUI;

public class MuestraAlarma {
        public String horario;
        public String tipo;
        public int minutosAntes;
        public boolean esMinutosAntes;

        public MuestraAlarma(String horario, String tipo) {
            this.horario = horario;
            this.tipo = tipo;
        }

        public MuestraAlarma(String horario, String tipo, int minutosAntes) {
            this.horario = horario;
            this.tipo = tipo;
            this.minutosAntes = minutosAntes;
            this.esMinutosAntes = true;
        }

        public String getHorario() {
            return horario;
        }
        public String getTipo() {
            return tipo;
        }
}