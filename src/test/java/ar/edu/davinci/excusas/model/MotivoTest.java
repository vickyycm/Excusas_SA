package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotivoTest {

    @Test
    @DisplayName("T77 Un motivo escrito como texto se convierte al enum")
    void t77_desdeTexto() {
        assertEquals(Motivo.PERDIDA_SUMINISTRO, Motivo.desde("PERDIDA_SUMINISTRO"));
    }

    @Test
    @DisplayName("T78 La conversión ignora mayúsculas y espacios")
    void t78_ignoraMayusculasYEspacios() {
        assertEquals(Motivo.QUEDARSE_DORMIDO, Motivo.desde("  quedarse_dormido "));
    }

    @Test
    @DisplayName("T79 Un motivo que no existe no se puede convertir")
    void t79_motivoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> Motivo.desde("ME_ABDUJERON"));
    }

    @Test
    @DisplayName("T80 Cada motivo crea un tipo de excusa nuevo")
    void t80_creaTipoNuevo() {
        assertNotSame(Motivo.INVEROSIMIL.crearTipo(), Motivo.INVEROSIMIL.crearTipo());
    }
}
