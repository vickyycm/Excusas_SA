package ar.edu.davinci.excusas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EncargadoConfigurableTest {

    private Email email;

    @BeforeEach
    void preparar() {
        email = mock(Email.class);
    }

    @Test
    @DisplayName("T88 Un encargado configurable acepta los motivos que se le indican")
    void t88_aceptaSusMotivos() {
        ExcusaI excusa = excusa(Motivo.IRRELEVANTE);

        coach().revisarExcusa(excusa);

        assertTrue(excusa.estaAceptada());
        assertEquals("Coach Motivacional", excusa.getAceptadaPor());
    }

    @Test
    @DisplayName("T89 Un encargado configurable deriva los motivos que no maneja")
    void t89_derivaLosAjenos() {
        EncargadoI siguiente = mock(EncargadoI.class);
        Encargado coach = coach();
        coach.setSiguiente(siguiente);
        ExcusaI excusa = excusa(Motivo.INVEROSIMIL);

        coach.revisarExcusa(excusa);

        verify(siguiente).manejar(excusa);
    }

    @Test
    @DisplayName("T90 Un encargado configurable avisa al empleado quién aceptó la excusa")
    void t90_avisaAlEmpleado() {
        coach().revisarExcusa(excusa(Motivo.QUEDARSE_DORMIDO));

        verify(email).enviarEmail("juan@excusas.com", "coach@excusas.com", "motivo demora",
                "Excusa aceptada por Coach Motivacional");
    }

    @Test
    @DisplayName("T91 Un encargado configurable informa los motivos que maneja")
    void t91_informaSusMotivos() {
        assertEquals(Set.of(Motivo.IRRELEVANTE, Motivo.QUEDARSE_DORMIDO), coach().getMotivos());
    }

    @Test
    @DisplayName("T92 Un encargado configurable puede sumarse a la línea en cualquier posición")
    void t92_enLaLinea() {
        ExcusaI excusa = excusa(Motivo.IRRELEVANTE);

        new Constructor()
                .agregar(new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal())))
                .agregar(coach())
                .construir()
                .manejar(excusa);

        assertEquals("Coach Motivacional", excusa.getAceptadaPor());
    }

    @Test
    @DisplayName("T93 Los encargados fijos informan los motivos que manejan")
    void t93_motivosDeLosFijos() {
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal()));

        assertEquals(Set.of(Motivo.QUEDARSE_DORMIDO, Motivo.PERDIDA_TRANSPORTE), recepcionista.getMotivos());
    }

    private Encargado coach() {
        return new EncargadoConfigurable("Coach Motivacional", "coach@excusas.com", 5001, email,
                new Delivery(new Normal()), Set.of(Motivo.IRRELEVANTE, Motivo.QUEDARSE_DORMIDO));
    }

    private ExcusaI excusa(Motivo motivo) {
        return new Excusa(new Empleado("Juan", "juan@excusas.com", 1001), motivo);
    }
}
