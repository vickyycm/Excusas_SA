package ar.edu.davinci;

public interface ExcusaI {
    void aceptar();
    void rechazar(String mensaje);
    boolean estaAceptada();
    String getRespuesta();
    TipoExcusa getTipo();
    EmpleadoI getEmpleado();
    void serManejadaPor(EncargadoI encargado);
}
