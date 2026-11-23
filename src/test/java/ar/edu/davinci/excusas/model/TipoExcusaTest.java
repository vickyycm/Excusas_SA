package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TipoExcusaTest {

    private EncargadoI encargado;

    @BeforeEach
    void preparar() {
        encargado = mock(EncargadoI.class);
    }

    @Test
    @DisplayName("T09 Quedarse dormido y perder el transporte son excusas triviales")
    void t09_triviales() {
        assertEquals(Trivial.class, Motivo.QUEDARSE_DORMIDO.crearTipo().getClass());
        assertEquals(Trivial.class, Motivo.PERDIDA_TRANSPORTE.crearTipo().getClass());
    }

    @Test
    @DisplayName("T10 Una pérdida de suministro es un corte de luz")
    void t10_corteLuz() {
        assertEquals(CorteLuz.class, Motivo.PERDIDA_SUMINISTRO.crearTipo().getClass());
    }

    @Test
    @DisplayName("T11 Cuidar a un familiar es un cuidado familiar")
    void t11_cuidadoFamiliar() {
        assertEquals(CuidadoFamiliar.class, Motivo.CUIDADO_FAMILIAR.crearTipo().getClass());
    }

    @Test
    @DisplayName("T12 Una excusa irrelevante es compleja")
    void t12_compleja() {
        assertEquals(Compleja.class, Motivo.IRRELEVANTE.crearTipo().getClass());
    }

    @Test
    @DisplayName("T13 Una excusa inverosímil crea su tipo inverosímil")
    void t13_inverosimil() {
        assertEquals(Inverosimil.class, Motivo.INVEROSIMIL.crearTipo().getClass());
    }

    @Test
    @DisplayName("T14 Un corte de luz consulta a EDESUR")
    void t14_corteLuzConsultaEdesur() {
        ExcusaI excusa = excusa(Motivo.PERDIDA_SUMINISTRO);

        excusa.getTipo().procesar(encargado, excusa);

        verify(encargado).enviarEmail(eq(CorteLuz.EMAIL_EDESUR), anyString(), anyString());
    }

    @Test
    @DisplayName("T15 Un cuidado familiar le pregunta al empleado si está todo bien")
    void t15_cuidadoFamiliarPregunta() {
        ExcusaI excusa = excusa(Motivo.CUIDADO_FAMILIAR);

        excusa.getTipo().procesar(encargado, excusa);

        verify(encargado).enviarEmail("juan@excusas.com", "motivo demora", "¿Está todo bien?");
    }

    @Test
    @DisplayName("T16 Una excusa trivial no tiene aviso propio")
    void t16_trivialSinAvisoPropio() {
        ExcusaI excusa = excusa(Motivo.QUEDARSE_DORMIDO);

        excusa.getTipo().procesar(encargado, excusa);

        verifyNoInteractions(encargado);
    }

    private ExcusaI excusa(Motivo motivo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), motivo);
    }
}
