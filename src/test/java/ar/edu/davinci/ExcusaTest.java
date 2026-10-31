package ar.edu.davinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcusaTest {

    @Test
    @DisplayName("T04 Una excusa nueva no está aceptada")
    void t04_nuevaNoAceptada() {
        assertFalse(excusa(new Trivial()).estaAceptada());
    }

    @Test
    @DisplayName("T05 Al aceptar una excusa queda aceptada")
    void t05_aceptarLaDejaAceptada() {
        ExcusaI excusa = excusa(new Trivial());

        excusa.aceptar();

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T06 Al rechazar una excusa guarda el mensaje de respuesta")
    void t06_rechazarGuardaRespuesta() {
        ExcusaI excusa = excusa(new Inverosimil());

        excusa.rechazar("Excusa rechazada: necesitamos pruebas contundentes");

        assertFalse(excusa.estaAceptada());
        assertEquals("Excusa rechazada: necesitamos pruebas contundentes", excusa.getRespuesta());
    }

    @Test
    @DisplayName("T07 Una excusa conoce al empleado que llegó tarde y su tipo")
    void t07_conoceEmpleadoYTipo() {
        EmpleadoI juan = juan();
        TipoExcusa tipo = new Compleja();

        ExcusaI excusa = new Excusa(juan, tipo);

        assertSame(juan, excusa.getEmpleado());
        assertSame(tipo, excusa.getTipo());
    }

    @Test
    @DisplayName("T08 La excusa le delega a su tipo cómo presentarse ante el encargado")
    void t08_delegaEnSuTipo() {
        TipoExcusa tipo = mock(TipoExcusa.class);
        EncargadoI encargado = mock(EncargadoI.class);
        ExcusaI excusa = new Excusa(juan(), tipo);

        excusa.serManejadaPor(encargado);

        verify(tipo).serManejadaPor(encargado, excusa);
    }

    private ExcusaI excusa(TipoExcusa tipo) {
        return new Excusa(juan(), tipo);
    }

    private EmpleadoI juan() {
        return new Empleado("Juan", "juan@excusas.com", 1001);
    }
}
