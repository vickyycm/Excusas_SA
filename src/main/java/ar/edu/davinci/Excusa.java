package ar.edu.davinci;

public class Excusa implements ExcusaI {
    private final EmpleadoI empleado;
    private final TipoExcusa tipo;
    private boolean aceptada;
    private String respuesta;

    public Excusa(EmpleadoI empleado, TipoExcusa tipo) {
        this.empleado = empleado;
        this.tipo = tipo;
        this.aceptada = false;
        this.respuesta = "";
    }

    @Override public void aceptar() { this.aceptada = true; }

    @Override public void rechazar(String mensaje) {
        this.aceptada = false;
        this.respuesta = mensaje;
    }

    @Override public boolean estaAceptada() { return aceptada; }
    @Override public String getRespuesta() { return respuesta; }
    @Override public TipoExcusa getTipo() { return tipo; }
    @Override public EmpleadoI getEmpleado() { return empleado; }

    @Override public void serManejadaPor(EncargadoI encargado) {
        tipo.serManejadaPor(encargado, this);
    }
}
