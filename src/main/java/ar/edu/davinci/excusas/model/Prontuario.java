package ar.edu.davinci.excusas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prontuarios")
public class Prontuario implements ProntuarioI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Empleado.class, optional = false)
    private EmpleadoI datosEmpleado;

    @OneToOne(targetEntity = Excusa.class, optional = false)
    private ExcusaI excusa;

    private int nroLegajo;

    protected Prontuario() {
    }

    public Prontuario(EmpleadoI datosEmpleado, ExcusaI excusa) {
        this.datosEmpleado = datosEmpleado;
        this.excusa = excusa;
        this.nroLegajo = datosEmpleado.getNumeroLegajo();
    }

    @Override public Long getId() { return id; }
    @Override public EmpleadoI getEmpleado() { return datosEmpleado; }
    @Override public ExcusaI getExcusa() { return excusa; }
    @Override public int getNroLegajo() { return nroLegajo; }
}
