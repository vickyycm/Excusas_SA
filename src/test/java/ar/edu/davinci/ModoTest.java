package ar.edu.davinci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ModoTest {

    private Email email;

    @BeforeEach
    void preparar() {
        email = mock(Email.class);
    }

    // ----- Productivo -----

    @Test
    @DisplayName("T43 En modo productivo acepta la excusa que le corresponde")
    void t43_productivoAcepta() {
        ExcusaI excusa = excusa(new Trivial());

        recepcionista(new Productivo()).revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T44 En modo productivo avisa al CTO antes de derivar")
    void t44_productivoAvisaAlCto() {
        recepcionista(new Productivo()).revisarExcusa(excusa(new Compleja()));

        verify(email).enviarEmail(eq(Productivo.EMAIL_CTO), eq("ana@excusas.com"), eq("excusa derivada"), anyString());
    }

    @Test
    @DisplayName("T45 En modo productivo, después de avisar al CTO pasa la excusa al siguiente")
    void t45_productivoDeriva() {
        EncargadoI siguiente = mock(EncargadoI.class);
        Encargado recepcionista = recepcionista(new Productivo());
        recepcionista.setSiguiente(siguiente);
        ExcusaI excusa = excusa(new Compleja());

        recepcionista.revisarExcusa(excusa);

        verify(siguiente).manejar(excusa);
    }

    // ----- Vago -----

    @Test
    @DisplayName("T46 En modo vago no evalúa la excusa aunque pudiera resolverla")
    void t46_vagoNoEvalua() {
        ExcusaI excusa = excusa(new Trivial());

        recepcionista(new Vago()).revisarExcusa(excusa);

        assertFalse(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T47 En modo vago pasa la excusa al siguiente")
    void t47_vagoDeriva() {
        EncargadoI siguiente = mock(EncargadoI.class);
        Encargado recepcionista = recepcionista(new Vago());
        recepcionista.setSiguiente(siguiente);
        ExcusaI excusa = excusa(new Trivial());

        recepcionista.revisarExcusa(excusa);

        verify(siguiente).manejar(excusa);
    }

    @Test
    @DisplayName("T48 En modo vago no avisa al CTO")
    void t48_vagoNoAvisaAlCto() {
        recepcionista(new Vago()).revisarExcusa(excusa(new Compleja()));

        verifyNoInteractions(email);
    }

    // ----- Normal -----

    @Test
    @DisplayName("T49 En modo normal deriva sin avisar al CTO")
    void t49_normalNoAvisaAlCto() {
        recepcionista(new Normal()).revisarExcusa(excusa(new Compleja()));

        verifyNoInteractions(email);
    }

    // ----- Ayudas -----

    private Encargado recepcionista(Modo modo) {
        return new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(modo));
    }

    private ExcusaI excusa(TipoExcusa tipo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), tipo);
    }
}
