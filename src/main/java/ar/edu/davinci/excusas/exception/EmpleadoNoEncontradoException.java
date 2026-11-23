package ar.edu.davinci.excusas.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(int legajo) {
        super("No existe el empleado con legajo " + legajo);
    }
}
