package ar.edu.davinci.excusas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado implements EmpleadoI {

    @Id
    private int numeroLegajo;
    private String nombre;
    private String email;

    protected Empleado() {
    }

    public Empleado(String nombre, String email, int numeroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.numeroLegajo = numeroLegajo;
    }

    @Override public String getNombre() { return nombre; }
    @Override public String getEmail() { return email; }
    @Override public int getNumeroLegajo() { return numeroLegajo; }
}
