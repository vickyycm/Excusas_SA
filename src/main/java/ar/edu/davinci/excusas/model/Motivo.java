package ar.edu.davinci.excusas.model;

import ar.edu.davinci.excusas.exception.MotivoInexistenteException;

import java.util.Arrays;
import java.util.function.Supplier;

//FACTORY: cada motivo sabe crear su tipo de excusa
public enum Motivo implements MotivoI {
    QUEDARSE_DORMIDO(Trivial::new),
    PERDIDA_TRANSPORTE(Trivial::new),
    PERDIDA_SUMINISTRO(CorteLuz::new),
    CUIDADO_FAMILIAR(CuidadoFamiliar::new),
    IRRELEVANTE(Compleja::new),
    INVEROSIMIL(Inverosimil::new);

    private final Supplier<TipoExcusa> creador;

    Motivo(Supplier<TipoExcusa> creador) {
        this.creador = creador;
    }

    @Override
    public TipoExcusa crearTipo() {
        return creador.get();
    }

    //texto de la api, si no existe lanza MotivoInexistenteException
    public static Motivo desde(String texto) {
        String buscado = texto.trim().toUpperCase();
        return Arrays.stream(values())
                .filter(valor -> valor.name().equals(buscado))
                .findFirst()
                .orElseThrow(() -> new MotivoInexistenteException(texto));
    }
}
