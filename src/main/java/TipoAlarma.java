public enum TipoAlarma implements Disparable {
    NOTIFICACION{
        @Override
        public void dipararAlarma() {
            //envio notificacion
        }
    },
    CORREO{
        @Override
        public void dipararAlarma() {
            // envio correo
        }
    },
    SONIDO{
        @Override
        public void dipararAlarma() {
            //envio sonido
        }
    };


}
