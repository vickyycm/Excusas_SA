package ar.edu.davinci.excusas.exception;

public class MotivoInexistenteException extends IllegalArgumentException {

    public MotivoInexistenteException(String motivo) {
        super("El motivo " + motivo + " no existe");
    }
}
