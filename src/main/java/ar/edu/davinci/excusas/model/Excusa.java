package ar.edu.davinci.excusas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "excusas")
public class Excusa implements ExcusaI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Empleado.class, optional = false)
    private EmpleadoI empleado;

    @Enumerated(EnumType.STRING)
    private Motivo motivo;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoExcusa estado;

    private String respuesta;
    private String aceptadaPor;

    protected Excusa() {
    }

    public Excusa(EmpleadoI empleado, Motivo motivo) {
        this(empleado, motivo, LocalDate.now());
    }

    public Excusa(EmpleadoI empleado, Motivo motivo, LocalDate fecha) {
        this.empleado = empleado;
        this.motivo = motivo;
        this.fecha = fecha;
        this.estado = EstadoExcusa.PENDIENTE;
        this.respuesta = "";
        this.aceptadaPor = "";
    }

    @Override public void aceptar(String encargado) {
        this.estado = EstadoExcusa.ACEPTADA;
        this.aceptadaPor = encargado;
    }

    @Override public void rechazar(String mensaje) {
        this.estado = EstadoExcusa.RECHAZADA;
        this.respuesta = mensaje;
    }

    //el tipo no se guarda, se crea a partir del motivo
    @Override public TipoExcusa getTipo() { return motivo.crearTipo(); }

    @Override public boolean estaAceptada() { return estado.estaAceptada(); }
    @Override public Long getId() { return id; }
    @Override public String getRespuesta() { return respuesta; }
    @Override public EmpleadoI getEmpleado() { return empleado; }
    @Override public Motivo getMotivo() { return motivo; }
    @Override public LocalDate getFecha() { return fecha; }
    @Override public EstadoExcusa getEstado() { return estado; }
    @Override public String getAceptadaPor() { return aceptadaPor; }
}
