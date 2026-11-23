package ar.edu.davinci.excusas.model;

import java.time.LocalDate;

public interface ExcusaI {
    Long getId();
    void aceptar(String encargado);
    void rechazar(String mensaje);
    boolean estaAceptada();
    String getRespuesta();
    TipoExcusa getTipo();
    EmpleadoI getEmpleado();
    Motivo getMotivo();
    LocalDate getFecha();
    EstadoExcusa getEstado();
    String getAceptadaPor();
}
