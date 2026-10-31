package ar.edu.davinci;

//ultimo de la cadena
public class Rechazador implements EncargadoI {

    public static final String MENSAJE_RECHAZO = "Excusa rechazada: necesitamos pruebas contundentes";

    @Override public void manejar(ExcusaI excusa) { rechazar(excusa); }
    @Override public void revisarExcusa(ExcusaI excusa) { rechazar(excusa); }
    @Override public void manejarTrivial(ExcusaI excusa) { rechazar(excusa); }
    @Override public void manejarModerada(ExcusaI excusa) { rechazar(excusa); }
    @Override public void manejarCompleja(ExcusaI excusa) { rechazar(excusa); }
    @Override public void manejarInverosimil(ExcusaI excusa) { rechazar(excusa); }

    @Override
    public void setSiguiente(EncargadoI siguiente) {
    }

    @Override
    public void enviarEmail(String destino, String asunto, String cuerpo) {
    }

    public String obtenerMensajeRechazo() {
        return MENSAJE_RECHAZO;
    }

    private void rechazar(ExcusaI excusa) {
        excusa.rechazar(MENSAJE_RECHAZO);
    }
}
