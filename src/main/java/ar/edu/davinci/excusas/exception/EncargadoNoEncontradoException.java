package ar.edu.davinci.excusas.exception;

public class EncargadoNoEncontradoException extends RuntimeException {

    public EncargadoNoEncontradoException(int legajo) {
        super("No existe el encargado con legajo " + legajo);
    }
}
