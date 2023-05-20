public enum TipoAlarma implements Disparable {
    NOTIFICACION{
        @Override
        public String dipararAlarma() {
            return "Enviando Notificacion";
        }
    },
    CORREO{
        @Override
        public String dipararAlarma() {
            return "Enviando Correo";
        }
    },
    SONIDO{
        @Override
        public String dipararAlarma() {
            return  "Enviando Sonido";
        }
    };
}
