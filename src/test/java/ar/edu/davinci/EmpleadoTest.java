package ar.edu.davinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    @Test
    @DisplayName("T01 Un empleado conoce su nombre")
    void t01_conoceSuNombre() {
        assertEquals("Juan", juan().getNombre());
    }

    @Test
    @DisplayName("T02 Un empleado conoce su email")
    void t02_conoceSuEmail() {
        assertEquals("juan@excusas.com", juan().getEmail());
    }

    @Test
    @DisplayName("T03 Un empleado conoce su número de legajo")
    void t03_conoceSuLegajo() {
        assertEquals(1001, juan().getNumeroLegajo());
    }

    private EmpleadoI juan() {
        return new Empleado("Juan", "juan@excusas.com", 1001);
    }
}
