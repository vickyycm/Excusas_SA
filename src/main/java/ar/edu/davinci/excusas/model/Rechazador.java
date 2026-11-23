package ar.edu.davinci.excusas.model;

import java.util.Set;

//ultimo de la cadena
public class Rechazador implements EncargadoI {

    public static final String MENSAJE_RECHAZO = "Excusa rechazada: necesitamos pruebas contundentes";

    @Override public void manejar(ExcusaI excusa) { rechazar(excusa); }
    @Override public void revisarExcusa(ExcusaI excusa) { rechazar(excusa); }

    @Override
    public void setSiguiente(EncargadoI siguiente) {
    }

    @Override
    public void enviarEmail(String destino, String asunto, String cuerpo) {
    }

    @Override
    public String getNombre() {
        return "Rechazador";
    }

    @Override
    public Set<Motivo> getMotivos() {
        return Set.of();
    }

    public String obtenerMensajeRechazo() {
        return MENSAJE_RECHAZO;
    }

    private void rechazar(ExcusaI excusa) {
        excusa.rechazar(MENSAJE_RECHAZO);
    }
}
