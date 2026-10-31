package ar.edu.davinci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LineaDeEncargadosTest {

    private Email email;

    @BeforeEach
    void preparar() {
        email = mock(Email.class);
    }

    @Test
    @DisplayName("T35 Una línea sin encargados rechaza la excusa")
    void t35_lineaVaciaRechaza() {
        ExcusaI excusa = excusa(new Trivial());

        new Constructor().construir().manejar(excusa);

        assertEquals(Rechazador.MENSAJE_RECHAZO, excusa.getRespuesta());
    }

    @Test
    @DisplayName("T36 El constructor engancha a cada encargado con el que le sigue")
    void t36_enganchaEnOrden() {
        EncargadoI primero = mock(EncargadoI.class);
        EncargadoI segundo = mock(EncargadoI.class);

        new Constructor().agregar(primero).agregar(segundo).construir();

        verify(primero).setSiguiente(segundo);
    }

    @Test
    @DisplayName("T37 El último encargado queda apuntando al rechazador")
    void t37_ultimoApuntaAlRechazador() {
        EncargadoI ultimo = mock(EncargadoI.class);

        new Constructor().agregar(ultimo).construir();

        verify(ultimo).setSiguiente(any(Rechazador.class));
    }

    @Test
    @DisplayName("T38 La línea le entrega la excusa al primer encargado")
    void t38_entregaAlPrimero() {
        EncargadoI primero = mock(EncargadoI.class);
        ExcusaI excusa = excusa(new Trivial());

        new Constructor().agregar(primero).construir().manejar(excusa);

        verify(primero).manejar(excusa);
    }

    @Test
    @DisplayName("T39 Un corte de luz recorre la cadena completa hasta la supervisora")
    void t39_corteLuzLlegaALaSupervisora() {
        ExcusaI excusa = excusa(new CorteLuz());

        lineaCompleta().manejar(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T40 El orden es configurable: una línea recepcionista y CEO acepta una inverosímil")
    void t40_ordenConfigurable() {
        ExcusaI excusa = excusa(new Inverosimil());

        new Constructor()
                .agregar(recepcionista())
                .agregar(ceo())
                .construir()
                .manejar(excusa);

        assertTrue(excusa.estaAceptada());
    }

    @Test
    @DisplayName("T41 Sin gerente en la línea, una excusa compleja se rechaza")
    void t41_sinGerenteSeRechazaCompleja() {
        ExcusaI excusa = excusa(new Compleja());

        new Constructor()
                .agregar(recepcionista())
                .agregar(supervisora())
                .agregar(ceo())
                .construir()
                .manejar(excusa);

        assertFalse(excusa.estaAceptada());
        assertEquals(Rechazador.MENSAJE_RECHAZO, excusa.getRespuesta());
    }

    private LineaDeEncargadosI lineaCompleta() {
        return new Constructor()
                .agregar(recepcionista())
                .agregar(supervisora())
                .agregar(gerente())
                .agregar(ceo())
                .construir();
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
