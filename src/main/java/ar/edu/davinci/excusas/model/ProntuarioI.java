package ar.edu.davinci.excusas.model;

public interface ProntuarioI {
    Long getId();
    EmpleadoI getEmpleado();
    ExcusaI getExcusa();
    int getNroLegajo();
}
