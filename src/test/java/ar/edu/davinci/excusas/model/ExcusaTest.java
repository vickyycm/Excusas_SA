package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcusaTest {

    @Test
    @DisplayName("T04 Una excusa nueva no está aceptada")
    void t04_nuevaNoAceptada() {
        assertFalse(excusa(Motivo.QUEDARSE_DORMIDO).estaAceptada());
    }

    @Test
    @DisplayName("T05 Al aceptar una excusa queda aceptada")
    void t05_aceptarLaDejaAceptada() {
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        excusa.aceptar("Ana");

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T06 Al rechazar una excusa guarda el mensaje de respuesta")
    void t06_rechazarGuardaRespuesta() {
        ExcusaI excusa = excusa(Motivo.INVEROSIMIL);

        excusa.rechazar("Excusa rechazada: necesitamos pruebas contundentes");

        assertFalse(excusa.estaAceptada());
        assertEquals("Excusa rechazada: necesitamos pruebas contundentes", excusa.getRespuesta());
    }

    @Test
    @DisplayName("T07 Una excusa conoce al empleado que llegó tarde y su motivo")
    void t07_conoceEmpleadoYMotivo() {
        EmpleadoI juan = juan();

        ExcusaI excusa = new Excusa(juan, Motivo.IRRELEVANTE);

        assertSame(juan, excusa.getEmpleado());
        assertEquals(Motivo.IRRELEVANTE, excusa.getMotivo());
    }

    @Test
    @DisplayName("T08 La excusa crea el tipo que corresponde a su motivo")
    void t08_creaSuTipo() {
        assertEquals(Compleja.class, excusa(Motivo.IRRELEVANTE).getTipo().getClass());
    }

    @Test
    @DisplayName("T84 Una excusa nueva queda pendiente y con la fecha de hoy")
    void t84_nuevaPendienteDeHoy() {
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        assertEquals(EstadoExcusa.PENDIENTE, excusa.getEstado());
        assertEquals(LocalDate.now(), excusa.getFecha());
    }

    @Test
    @DisplayName("T85 Una excusa puede registrarse con una fecha determinada")
    void t85_fechaIndicada() {
        LocalDate fecha = LocalDate.of(2026, 11, 2);

        assertEquals(fecha, new Excusa(juan(), Motivo.QUEDARSE_DORMIDO, fecha).getFecha());
    }

    @Test
    @DisplayName("T86 Al aceptarla queda aceptada y registra quién la aceptó")
    void t86_aceptadaPor() {
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        excusa.aceptar("Ana");

        assertEquals(EstadoExcusa.ACEPTADA, excusa.getEstado());
        assertEquals("Ana", excusa.getAceptadaPor());
    }

    @Test
    @DisplayName("T87 Al rechazarla queda en estado rechazada")
    void t87_rechazada() {
        ExcusaI excusa = excusa(Motivo.INVEROSIMIL);

        excusa.rechazar(Rechazador.MENSAJE_RECHAZO);

        assertEquals(EstadoExcusa.RECHAZADA, excusa.getEstado());
        assertEquals("", excusa.getAceptadaPor());
    }

    private ExcusaI excusa(Motivo motivo) {
        return new Excusa(juan(), motivo);
    }

    private EmpleadoI juan() {
        return new Empleado("Juan", "juan@excusas.com", 1001);
    }
}
