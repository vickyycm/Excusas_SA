package ar.edu.davinci;

public class Prontuario implements ProntuarioI {

    private final EmpleadoI datosEmpleado;
    private final ExcusaI excusa;
    private final int nroLegajo;

    public Prontuario(EmpleadoI datosEmpleado, ExcusaI excusa) {
        this.datosEmpleado = datosEmpleado;
        this.excusa = excusa;
        this.nroLegajo = datosEmpleado.getNumeroLegajo();
    }

    @Override public EmpleadoI getEmpleado() { return datosEmpleado; }
    @Override public ExcusaI getExcusa() { return excusa; }
    @Override public int getNroLegajo() { return nroLegajo; }
}
