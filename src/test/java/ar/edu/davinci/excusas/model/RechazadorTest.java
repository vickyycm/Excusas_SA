package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RechazadorTest {

    @Test
    @DisplayName("T32 El rechazador rechaza la excusa con el mensaje del enunciado")
    void t32_rechazaConMensaje() {
        ExcusaI excusa = excusa(Motivo.INVEROSIMIL);

        new Rechazador().manejar(excusa);

        assertFalse(excusa.estaAceptada());
        assertEquals("Excusa rechazada: necesitamos pruebas contundentes", excusa.getRespuesta());
    }

    @Test
    @DisplayName("T33 El rechazador rechaza cualquier tipo de excusa")
    void t33_rechazaCualquierTipo() {
        Rechazador rechazador = new Rechazador();
        ExcusaI trivial = excusa(Motivo.QUEDARSE_DORMIDO);
        ExcusaI moderada = excusa(Motivo.PERDIDA_SUMINISTRO);
        ExcusaI compleja = excusa(Motivo.IRRELEVANTE);

        rechazador.manejar(trivial);
        rechazador.revisarExcusa(moderada);
        rechazador.manejar(compleja);

        assertEquals(Rechazador.MENSAJE_RECHAZO, trivial.getRespuesta());
        assertEquals(Rechazador.MENSAJE_RECHAZO, moderada.getRespuesta());
        assertEquals(Rechazador.MENSAJE_RECHAZO, compleja.getRespuesta());
    }

    @Test
    @DisplayName("T34 El rechazador informa su mensaje de rechazo")
    void t34_informaMensaje() {
        assertEquals(Rechazador.MENSAJE_RECHAZO, new Rechazador().obtenerMensajeRechazo());
    }

    @Test
    @DisplayName("T94 El rechazador no maneja ningún motivo y se identifica como Rechazador")
    void t94_sinMotivos() {
        Rechazador rechazador = new Rechazador();

        assertTrue(rechazador.getMotivos().isEmpty());
        assertEquals("Rechazador", rechazador.getNombre());
    }

    private ExcusaI excusa(Motivo motivo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), motivo);
    }
}
