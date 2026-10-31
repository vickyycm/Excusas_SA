package ar.edu.davinci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TipoExcusaTest {

    private EncargadoI encargado;

    @BeforeEach
    void preparar() {
        encargado = mock(EncargadoI.class);
    }

    @Test
    @DisplayName("T09 Una excusa trivial se presenta como trivial")
    void t09_trivial() {
        ExcusaI excusa = excusa(new Trivial());

        excusa.serManejadaPor(encargado);

        verify(encargado).manejarTrivial(excusa);
    }

    @Test
    @DisplayName("T10 Un corte de luz se presenta como excusa moderada")
    void t10_corteLuzEsModerada() {
        ExcusaI excusa = excusa(new CorteLuz());

        excusa.serManejadaPor(encargado);

        verify(encargado).manejarModerada(excusa);
    }

    @Test
    @DisplayName("T11 Un cuidado familiar se presenta como excusa moderada")
    void t11_cuidadoFamiliarEsModerada() {
        ExcusaI excusa = excusa(new CuidadoFamiliar());

        excusa.serManejadaPor(encargado);

        verify(encargado).manejarModerada(excusa);
    }

    @Test
    @DisplayName("T12 Una excusa compleja se presenta como compleja")
    void t12_compleja() {
        ExcusaI excusa = excusa(new Compleja());

        excusa.serManejadaPor(encargado);

        verify(encargado).manejarCompleja(excusa);
    }

    @Test
    @DisplayName("T13 Una excusa inverosímil se presenta como inverosímil")
    void t13_inverosimil() {
        ExcusaI excusa = excusa(new Inverosimil());

        excusa.serManejadaPor(encargado);

        verify(encargado).manejarInverosimil(excusa);
    }

    @Test
    @DisplayName("T14 Un corte de luz consulta a EDESUR")
    void t14_corteLuzConsultaEdesur() {
        ExcusaI excusa = excusa(new CorteLuz());

        excusa.getTipo().procesar(encargado, excusa);

        verify(encargado).enviarEmail(eq(CorteLuz.EMAIL_EDESUR), anyString(), anyString());
    }

    @Test
    @DisplayName("T15 Un cuidado familiar le pregunta al empleado si está todo bien")
    void t15_cuidadoFamiliarPregunta() {
        ExcusaI excusa = excusa(new CuidadoFamiliar());

        excusa.getTipo().procesar(encargado, excusa);

        verify(encargado).enviarEmail("juan@excusas.com", "motivo demora", "¿Está todo bien?");
    }

    @Test
    @DisplayName("T16 Una excusa trivial no tiene aviso propio")
    void t16_trivialSinAvisoPropio() {
        ExcusaI excusa = excusa(new Trivial());

        excusa.getTipo().procesar(encargado, excusa);

        verifyNoInteractions(encargado);
    }

    private ExcusaI excusa(TipoExcusa tipo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), tipo);
    }
}
