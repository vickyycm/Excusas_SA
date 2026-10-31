package ar.edu.davinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RechazadorTest {

    @Test
    @DisplayName("T32 El rechazador rechaza la excusa con el mensaje del enunciado")
    void t32_rechazaConMensaje() {
        ExcusaI excusa = excusa(new Inverosimil());

        new Rechazador().manejar(excusa);

        assertFalse(excusa.estaAceptada());
        assertEquals("Excusa rechazada: necesitamos pruebas contundentes", excusa.getRespuesta());
    }

    @Test
    @DisplayName("T33 El rechazador rechaza cualquier tipo de excusa")
    void t33_rechazaCualquierTipo() {
        Rechazador rechazador = new Rechazador();
        ExcusaI trivial = excusa(new Trivial());
        ExcusaI moderada = excusa(new CorteLuz());
        ExcusaI compleja = excusa(new Compleja());

        rechazador.manejarTrivial(trivial);
        rechazador.manejarModerada(moderada);
        rechazador.manejarCompleja(compleja);

        assertEquals(Rechazador.MENSAJE_RECHAZO, trivial.getRespuesta());
        assertEquals(Rechazador.MENSAJE_RECHAZO, moderada.getRespuesta());
        assertEquals(Rechazador.MENSAJE_RECHAZO, compleja.getRespuesta());
    }

    @Test
    @DisplayName("T34 El rechazador informa su mensaje de rechazo")
    void t34_informaMensaje() {
        assertEquals(Rechazador.MENSAJE_RECHAZO, new Rechazador().obtenerMensajeRechazo());
    }

    private ExcusaI excusa(TipoExcusa tipo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), tipo);
    }
}
