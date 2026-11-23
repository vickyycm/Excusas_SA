package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModoTrabajoTest {

    @ParameterizedTest(name = "T81 {0} crea un modo que se reconoce como {0}")
    @DisplayName("T81 Cada modo de trabajo crea su estrategia")
    @EnumSource(ModoTrabajo.class)
    void t81_creaSuModo(ModoTrabajo modoTrabajo) {
        assertEquals(modoTrabajo, modoTrabajo.crearModo().getModoTrabajo());
    }

    @Test
    @DisplayName("T82 Un modo escrito como texto se convierte al enum")
    void t82_desdeTexto() {
        assertEquals(ModoTrabajo.VAGO, ModoTrabajo.desde(" vago "));
    }

    @Test
    @DisplayName("T83 Un modo que no existe no se puede convertir")
    void t83_modoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> ModoTrabajo.desde("DORMIDO"));
    }
}
