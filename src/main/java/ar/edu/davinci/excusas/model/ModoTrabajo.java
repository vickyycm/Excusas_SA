package ar.edu.davinci.excusas.model;

import ar.edu.davinci.excusas.exception.ModoInexistenteException;

import java.util.Arrays;
import java.util.function.Supplier;

//FACTORY: cada modo de trabajo sabe crear su estrategia
public enum ModoTrabajo implements ModoTrabajoI {
    NORMAL(Normal::new),
    VAGO(Vago::new),
    PRODUCTIVO(Productivo::new);

    private final Supplier<Modo> creador;

    ModoTrabajo(Supplier<Modo> creador) {
        this.creador = creador;
    }

    @Override
    public Modo crearModo() {
        return creador.get();
    }

    public static ModoTrabajo desde(String texto) {
        String buscado = texto.trim().toUpperCase();
        return Arrays.stream(values())
                .filter(valor -> valor.name().equals(buscado))
                .findFirst()
                .orElseThrow(() -> new ModoInexistenteException(texto));
    }
}
