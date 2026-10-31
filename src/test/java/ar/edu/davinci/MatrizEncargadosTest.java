package ar.edu.davinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MatrizEncargadosTest {

    @ParameterizedTest(name = "T42 {0} con excusa {1} → aceptada: {2}")
    @DisplayName("T42 Cada encargado acepta solo su tipo de excusa")
    @MethodSource("combinaciones")
    void t42_cadaEncargadoAceptaSoloLaSuya(String encargado, String tipo, boolean aceptada,
                                           Function<Email, Encargado> crearEncargado,
                                           Supplier<TipoExcusa> crearTipo) {
        ExcusaI excusa = new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), crearTipo.get());

        crearEncargado.apply(mock(Email.class)).revisarExcusa(excusa);

        assertEquals(aceptada, excusa.estaAceptada());
    }

    static Stream<Arguments> combinaciones() {
        Function<Email, Encargado> recepcionista =
                email -> new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal()));
        Function<Email, Encargado> supervisora =
                email -> new SupervisorArea("Lucia", "lucia@excusas.com", 2002, email, new Delivery(new Normal()));
        Function<Email, Encargado> gerente =
                email -> new GerenteRRHH("Pedro", "pedro@excusas.com", 2003, email, new Delivery(new Normal()));
        Function<Email, Encargado> ceo =
                email -> new CEO("Marta", "marta@excusas.com", 2004, email, new Delivery(new Normal()));

        return Stream.of(
                Arguments.of("Recepcionista", "Trivial", true, recepcionista, (Supplier<TipoExcusa>) Trivial::new),
                Arguments.of("Recepcionista", "CorteLuz", false, recepcionista, (Supplier<TipoExcusa>) CorteLuz::new),
                Arguments.of("Recepcionista", "CuidadoFamiliar", false, recepcionista, (Supplier<TipoExcusa>) CuidadoFamiliar::new),
                Arguments.of("Recepcionista", "Compleja", false, recepcionista, (Supplier<TipoExcusa>) Compleja::new),
                Arguments.of("Recepcionista", "Inverosimil", false, recepcionista, (Supplier<TipoExcusa>) Inverosimil::new),

                Arguments.of("Supervisora", "Trivial", false, supervisora, (Supplier<TipoExcusa>) Trivial::new),
                Arguments.of("Supervisora", "CorteLuz", true, supervisora, (Supplier<TipoExcusa>) CorteLuz::new),
                Arguments.of("Supervisora", "CuidadoFamiliar", true, supervisora, (Supplier<TipoExcusa>) CuidadoFamiliar::new),
                Arguments.of("Supervisora", "Compleja", false, supervisora, (Supplier<TipoExcusa>) Compleja::new),
                Arguments.of("Supervisora", "Inverosimil", false, supervisora, (Supplier<TipoExcusa>) Inverosimil::new),

                Arguments.of("Gerente", "Trivial", false, gerente, (Supplier<TipoExcusa>) Trivial::new),
                Arguments.of("Gerente", "CorteLuz", false, gerente, (Supplier<TipoExcusa>) CorteLuz::new),
                Arguments.of("Gerente", "CuidadoFamiliar", false, gerente, (Supplier<TipoExcusa>) CuidadoFamiliar::new),
                Arguments.of("Gerente", "Compleja", true, gerente, (Supplier<TipoExcusa>) Compleja::new),
                Arguments.of("Gerente", "Inverosimil", false, gerente, (Supplier<TipoExcusa>) Inverosimil::new),

                Arguments.of("CEO", "Trivial", false, ceo, (Supplier<TipoExcusa>) Trivial::new),
                Arguments.of("CEO", "CorteLuz", false, ceo, (Supplier<TipoExcusa>) CorteLuz::new),
                Arguments.of("CEO", "CuidadoFamiliar", false, ceo, (Supplier<TipoExcusa>) CuidadoFamiliar::new),
                Arguments.of("CEO", "Compleja", false, ceo, (Supplier<TipoExcusa>) Compleja::new),
                Arguments.of("CEO", "Inverosimil", true, ceo, (Supplier<TipoExcusa>) Inverosimil::new)
        );
    }
}
