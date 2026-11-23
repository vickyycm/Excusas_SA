package ar.edu.davinci.excusas.exception;

public class ModoInexistenteException extends IllegalArgumentException {

    public ModoInexistenteException(String modo) {
        super("El modo " + modo + " no existe");
    }
}
