package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EncargadoTest {

    private Email email;

    @BeforeEach
    void preparar() {
        email = mock(Email.class);
    }

    @Test
    @DisplayName("T17 La recepcionista acepta una excusa trivial")
    void t17_recepcionistaAceptaTrivial() {
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        recepcionista().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T18 La recepcionista avisa al empleado que la licencia fue aceptada")
    void t18_recepcionistaAvisaAceptacion() {
        recepcionista().revisarExcusa(excusa(Motivo.QUEDARSE_DORMIDO));

        verify(email).enviarEmail("juan@excusas.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
    }

    @Test
    @DisplayName("T19 La recepcionista pasa al siguiente una excusa que no le corresponde")
    void t19_recepcionistaDeriva() {
        EncargadoI siguiente = mock(EncargadoI.class);
        Encargado recepcionista = recepcionista();
        recepcionista.setSiguiente(siguiente);
        ExcusaI excusa = excusa(Motivo.PERDIDA_SUMINISTRO);

        recepcionista.revisarExcusa(excusa);

        verify(siguiente).manejar(excusa);
    }

    @Test
    @DisplayName("T20 La supervisora acepta un corte de luz y consulta a EDESUR")
    void t20_supervisoraCorteLuz() {
        ExcusaI excusa = excusa(Motivo.PERDIDA_SUMINISTRO);

        supervisora().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(email).enviarEmail(eq(CorteLuz.EMAIL_EDESUR), eq("lucia@excusas.com"), anyString(), anyString());
    }

    @Test
    @DisplayName("T21 La supervisora acepta un cuidado familiar y pregunta si está todo bien")
    void t21_supervisoraCuidadoFamiliar() {
        ExcusaI excusa = excusa(Motivo.CUIDADO_FAMILIAR);

        supervisora().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(email).enviarEmail("juan@excusas.com", "lucia@excusas.com", "motivo demora", "¿Está todo bien?");
    }

    @Test
    @DisplayName("T22 La supervisora no acepta una excusa trivial")
    void t22_supervisoraNoAceptaTrivial() {
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        supervisora().revisarExcusa(excusa);

        assertFalse(excusa.estaAceptada());
    }


    @Test
    @DisplayName("T23 El gerente de RRHH acepta una excusa compleja")
    void t23_gerenteAceptaCompleja() {
        ExcusaI excusa = excusa(Motivo.IRRELEVANTE);

        gerente().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T24 El gerente de RRHH no envía mails al aceptar")
    void t24_gerenteSinMails() {
        gerente().revisarExcusa(excusa(Motivo.IRRELEVANTE));

        verifyNoInteractions(email);
    }

    @Test
    @DisplayName("T25 El CEO acepta una excusa inverosímil")
    void t25_ceoAceptaInverosimil() {
        ExcusaI excusa = excusa(Motivo.INVEROSIMIL);

        ceo().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T26 El CEO responde Aprobado por creatividad")
    void t26_ceoRespondeCreatividad() {
        ceo().revisarExcusa(excusa(Motivo.INVEROSIMIL));

        verify(email).enviarEmail("juan@excusas.com", "marta@excusas.com", "motivo demora", "Aprobado por creatividad");
    }

    @Test
    @DisplayName("T27 El CEO no acepta una excusa compleja")
    void t27_ceoNoAceptaCompleja() {
        ExcusaI excusa = excusa(Motivo.IRRELEVANTE);

        ceo().revisarExcusa(excusa);

        assertFalse(excusa.estaAceptada());
    }


    @Test
    @DisplayName("T28 Si nadie más la toma, la excusa queda rechazada")
    void t28_sinSiguienteSeRechaza() {
        ExcusaI excusa = excusa(Motivo.INVEROSIMIL);

        recepcionista().revisarExcusa(excusa);

        assertEquals(Rechazador.MENSAJE_RECHAZO, excusa.getRespuesta());
    }


    @Test
    @DisplayName("T29 Procesar acepta la excusa antes de notificar")
    void t29_procesarRespetaElOrden() {
        ExcusaI excusa = mock(ExcusaI.class);
        when(excusa.getEmpleado()).thenReturn(new Empleado("Juan", "juan@excusas.com", 1001));

        recepcionista().procesar(excusa);

        InOrder orden = inOrder(excusa, email);
        orden.verify(excusa).aceptar("Ana");
        orden.verify(email).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("T30 Al procesar, el encargado le avisa a su delivery que procesó una excusa")
    void t30_procesarCuentaEnElDelivery() {
        DeliveryI delivery = mock(DeliveryI.class);
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, email, delivery);

        recepcionista.procesar(excusa(Motivo.QUEDARSE_DORMIDO));

        verify(delivery).excusaProcesada();
    }

    @Test
    @DisplayName("T31 Revisar una excusa se la entrega al delivery")
    void t31_revisarDelegaEnDelivery() {
        DeliveryI delivery = mock(DeliveryI.class);
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, email, delivery);
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        recepcionista.manejar(excusa);

        verify(delivery).entregar(recepcionista, excusa);
    }


    private Encargado recepcionista() {
        return new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal()));
    }

    private Encargado supervisora() {
        return new SupervisorArea("Lucia", "lucia@excusas.com", 2002, email, new Delivery(new Normal()));
    }

    private Encargado gerente() {
        return new GerenteRRHH("Pedro", "pedro@excusas.com", 2003, email, new Delivery(new Normal()));
    }

    private Encargado ceo() {
        return new CEO("Marta", "marta@excusas.com", 2004, email, new Delivery(new Normal()));
    }

    private ExcusaI excusa(Motivo motivo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), motivo);
    }
}
