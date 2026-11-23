package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MatrizEncargadosTest {

    @ParameterizedTest(name = "T42 {0} con motivo {1} → aceptada: {2}")
    @DisplayName("T42 Cada encargado acepta solo su tipo de excusa")
    @MethodSource("combinaciones")
    void t42_cadaEncargadoAceptaSoloLaSuya(String encargado, String motivoTexto, boolean aceptada,
                                           Function<Email, Encargado> crearEncargado,
                                           Motivo motivo) {
        ExcusaI excusa = new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), motivo);

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
                Arguments.of("Recepcionista", "QUEDARSE_DORMIDO", true, recepcionista, Motivo.QUEDARSE_DORMIDO),
                Arguments.of("Recepcionista", "PERDIDA_SUMINISTRO", false, recepcionista, Motivo.PERDIDA_SUMINISTRO),
                Arguments.of("Recepcionista", "CUIDADO_FAMILIAR", false, recepcionista, Motivo.CUIDADO_FAMILIAR),
                Arguments.of("Recepcionista", "IRRELEVANTE", false, recepcionista, Motivo.IRRELEVANTE),
                Arguments.of("Recepcionista", "INVEROSIMIL", false, recepcionista, Motivo.INVEROSIMIL),

                Arguments.of("Supervisora", "QUEDARSE_DORMIDO", false, supervisora, Motivo.QUEDARSE_DORMIDO),
                Arguments.of("Supervisora", "PERDIDA_SUMINISTRO", true, supervisora, Motivo.PERDIDA_SUMINISTRO),
                Arguments.of("Supervisora", "CUIDADO_FAMILIAR", true, supervisora, Motivo.CUIDADO_FAMILIAR),
                Arguments.of("Supervisora", "IRRELEVANTE", false, supervisora, Motivo.IRRELEVANTE),
                Arguments.of("Supervisora", "INVEROSIMIL", false, supervisora, Motivo.INVEROSIMIL),

                Arguments.of("Gerente", "QUEDARSE_DORMIDO", false, gerente, Motivo.QUEDARSE_DORMIDO),
                Arguments.of("Gerente", "PERDIDA_SUMINISTRO", false, gerente, Motivo.PERDIDA_SUMINISTRO),
                Arguments.of("Gerente", "CUIDADO_FAMILIAR", false, gerente, Motivo.CUIDADO_FAMILIAR),
                Arguments.of("Gerente", "IRRELEVANTE", true, gerente, Motivo.IRRELEVANTE),
                Arguments.of("Gerente", "INVEROSIMIL", false, gerente, Motivo.INVEROSIMIL),

                Arguments.of("CEO", "QUEDARSE_DORMIDO", false, ceo, Motivo.QUEDARSE_DORMIDO),
                Arguments.of("CEO", "PERDIDA_SUMINISTRO", false, ceo, Motivo.PERDIDA_SUMINISTRO),
                Arguments.of("CEO", "CUIDADO_FAMILIAR", false, ceo, Motivo.CUIDADO_FAMILIAR),
                Arguments.of("CEO", "IRRELEVANTE", false, ceo, Motivo.IRRELEVANTE),
                Arguments.of("CEO", "INVEROSIMIL", true, ceo, Motivo.INVEROSIMIL)
        );
    }
}
