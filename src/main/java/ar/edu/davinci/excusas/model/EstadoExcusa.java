package ar.edu.davinci.excusas.model;

public enum EstadoExcusa implements EstadoExcusaI {
    PENDIENTE(false),
    ACEPTADA(true),
    RECHAZADA(false);

    private final boolean aceptada;

    EstadoExcusa(boolean aceptada) {
        this.aceptada = aceptada;
    }

    @Override
    public boolean estaAceptada() {
        return aceptada;
    }
}
