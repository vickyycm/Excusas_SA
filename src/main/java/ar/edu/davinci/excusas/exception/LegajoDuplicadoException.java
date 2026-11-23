package ar.edu.davinci.excusas.exception;

public class LegajoDuplicadoException extends RuntimeException {

    public LegajoDuplicadoException(int legajo) {
        super("Ya existe alguien con el legajo " + legajo);
    }
}
