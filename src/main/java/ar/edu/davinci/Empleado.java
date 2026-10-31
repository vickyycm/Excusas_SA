package ar.edu.davinci;

public class Empleado implements EmpleadoI {
    private final String nombre;
    private final String email;
    private final int numeroLegajo;

    public Empleado(String nombre, String email, int numeroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.numeroLegajo = numeroLegajo;
    }

    @Override public String getNombre() { return nombre; }
    @Override public String getEmail() { return email; }
    @Override public int getNumeroLegajo() { return numeroLegajo; }
}
