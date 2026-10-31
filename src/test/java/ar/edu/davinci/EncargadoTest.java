package ar.edu.davinci;

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
        ExcusaI excusa = excusa(new Trivial());

        recepcionista().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T18 La recepcionista avisa al empleado que la licencia fue aceptada")
    void t18_recepcionistaAvisaAceptacion() {
        recepcionista().revisarExcusa(excusa(new Trivial()));

        verify(email).enviarEmail("juan@excusas.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
    }

    @Test
    @DisplayName("T19 La recepcionista pasa al siguiente una excusa que no le corresponde")
    void t19_recepcionistaDeriva() {
        EncargadoI siguiente = mock(EncargadoI.class);
        Encargado recepcionista = recepcionista();
        recepcionista.setSiguiente(siguiente);
        ExcusaI excusa = excusa(new CorteLuz());

        recepcionista.revisarExcusa(excusa);

        verify(siguiente).manejar(excusa);
    }

    @Test
    @DisplayName("T20 La supervisora acepta un corte de luz y consulta a EDESUR")
    void t20_supervisoraCorteLuz() {
        ExcusaI excusa = excusa(new CorteLuz());

        supervisora().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(email).enviarEmail(eq(CorteLuz.EMAIL_EDESUR), eq("lucia@excusas.com"), anyString(), anyString());
    }

    @Test
    @DisplayName("T21 La supervisora acepta un cuidado familiar y pregunta si está todo bien")
    void t21_supervisoraCuidadoFamiliar() {
        ExcusaI excusa = excusa(new CuidadoFamiliar());

        supervisora().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        verify(email).enviarEmail("juan@excusas.com", "lucia@excusas.com", "motivo demora", "¿Está todo bien?");
    }

    @Test
    @DisplayName("T22 La supervisora no acepta una excusa trivial")
    void t22_supervisoraNoAceptaTrivial() {
        ExcusaI excusa = excusa(new Trivial());

        supervisora().revisarExcusa(excusa);

        assertFalse(excusa.estaAceptada());
    }


    @Test
    @DisplayName("T23 El gerente de RRHH acepta una excusa compleja")
    void t23_gerenteAceptaCompleja() {
        ExcusaI excusa = excusa(new Compleja());

        gerente().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T24 El gerente de RRHH no envía mails al aceptar")
    void t24_gerenteSinMails() {
        gerente().revisarExcusa(excusa(new Compleja()));

        verifyNoInteractions(email);
    }

    @Test
    @DisplayName("T25 El CEO acepta una excusa inverosímil")
    void t25_ceoAceptaInverosimil() {
        ExcusaI excusa = excusa(new Inverosimil());

        ceo().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T26 El CEO responde Aprobado por creatividad")
    void t26_ceoRespondeCreatividad() {
        ceo().revisarExcusa(excusa(new Inverosimil()));

        verify(email).enviarEmail("juan@excusas.com", "marta@excusas.com", "motivo demora", "Aprobado por creatividad");
    }

    @Test
    @DisplayName("T27 El CEO no acepta una excusa compleja")
    void t27_ceoNoAceptaCompleja() {
        ExcusaI excusa = excusa(new Compleja());

        ceo().revisarExcusa(excusa);

        assertFalse(excusa.estaAceptada());
    }


    @Test
    @DisplayName("T28 Si nadie más la toma, la excusa queda rechazada")
    void t28_sinSiguienteSeRechaza() {
        ExcusaI excusa = excusa(new Inverosimil());

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
        orden.verify(excusa).aceptar();
        orden.verify(email).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("T30 Al procesar, el encargado le avisa a su delivery que procesó una excusa")
    void t30_procesarCuentaEnElDelivery() {
        DeliveryI delivery = mock(DeliveryI.class);
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, email, delivery);

        recepcionista.procesar(excusa(new Trivial()));

        verify(delivery).excusaProcesada();
    }

    @Test
    @DisplayName("T31 Revisar una excusa se la entrega al delivery")
    void t31_revisarDelegaEnDelivery() {
        DeliveryI delivery = mock(DeliveryI.class);
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, email, delivery);
        ExcusaI excusa = excusa(new Trivial());

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

    private ExcusaI excusa(TipoExcusa tipo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), tipo);
    }
}
