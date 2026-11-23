package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    @DisplayName("T119 La demo de la consola recorre todos los casos sin errores")
    void t119_demoCompleta() {
        int antes = AdministradorProntuario.getInstancia().cantidadProntuarios();

        assertDoesNotThrow(() -> Main.main(new String[]{}));

        assertTrue(AdministradorProntuario.getInstancia().cantidadProntuarios() > antes);
    }
}
