package ar.edu.davinci;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProntuarioTest {

    private AdministradorProntuario administrador;
    private Observador observador;
    private EquipoDireccion direccion;
    private Email email;

    @BeforeEach
    void preparar() {
        administrador = AdministradorProntuario.getInstancia();
        observador = mock(Observador.class);
        direccion = new EquipoDireccion();
        email = mock(Email.class);
        administrador.agregarObservador(observador);
        administrador.agregarObservador(direccion);
    }

    // El Singleton vive durante todos los tests: al terminar, sacamos lo que agregamos
    @AfterEach
    void limpiar() {
        administrador.eliminarObservador(observador);
        administrador.eliminarObservador(direccion);
    }

    // ----- Singleton -----

    @Test
    @DisplayName("T66 El administrador de prontuarios es único")
    void t66_esUnico() {
        assertSame(AdministradorProntuario.getInstancia(), AdministradorProntuario.getInstancia());
    }

    @Test
    @DisplayName("T67 Persistir un prontuario lo guarda en el administrador")
    void t67_persistirGuarda() {
        int antes = administrador.cantidadProntuarios();

        administrador.persistirProntuario(prontuario());

        assertEquals(antes + 1, administrador.cantidadProntuarios());
    }

    // ----- Observer -----

    @Test
    @DisplayName("T68 Al persistir un prontuario se avisa a los observadores")
    void t68_avisaAlPersistir() {
        ProntuarioI prontuario = prontuario();

        administrador.persistirProntuario(prontuario);

        verify(observador).actualizar(prontuario);
    }

    @Test
    @DisplayName("T69 Un observador eliminado ya no recibe avisos")
    void t69_eliminadoNoRecibe() {
        administrador.eliminarObservador(observador);

        administrador.persistirProntuario(prontuario());

        verifyNoInteractions(observador);
    }

    @Test
    @DisplayName("T70 Se avisa a todos los observadores registrados")
    void t70_avisaATodos() {
        administrador.persistirProntuario(prontuario());

        verify(observador).actualizar(any(ProntuarioI.class));
        assertEquals(1, direccion.cantidadAvisosRecibidos());
    }

    // ----- Prontuario -----

    @Test
    @DisplayName("T71 El prontuario guarda el empleado, la excusa y el legajo")
    void t71_datosDelProntuario() {
        EmpleadoI juan = juan();
        ExcusaI excusa = new Excusa(juan, new Inverosimil());

        ProntuarioI prontuario = new Prontuario(juan, excusa);

        assertSame(juan, prontuario.getEmpleado());
        assertSame(excusa, prontuario.getExcusa());
        assertEquals(1001, prontuario.getNroLegajo());
    }

    // ----- CEO -----

    @Test
    @DisplayName("T72 Cuando el CEO acepta una excusa se genera un prontuario")
    void t72_ceoGeneraProntuario() {
        int antes = administrador.cantidadProntuarios();

        ceo().revisarExcusa(new Excusa(juan(), new Inverosimil()));

        assertEquals(antes + 1, administrador.cantidadProntuarios());
    }

    @Test
    @DisplayName("T73 Cuando el CEO genera un prontuario, la dirección se entera")
    void t73_direccionSeEntera() {
        ExcusaI excusa = new Excusa(juan(), new Inverosimil());

        ceo().revisarExcusa(excusa);

        assertEquals(1, direccion.cantidadAvisosRecibidos());
        assertSame(excusa, direccion.getRecibidos().get(0).getExcusa());
    }

    @Test
    @DisplayName("T74 Una excusa que resuelve la recepcionista no genera prontuario")
    void t74_trivialSinProntuario() {
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal()));

        recepcionista.revisarExcusa(new Excusa(juan(), new Trivial()));

        assertEquals(0, direccion.cantidadAvisosRecibidos());
    }

    @Test
    @DisplayName("T75 El CEO también puede observar los prontuarios")
    void t75_ceoObservador() {
        CEO ceo = ceo();
        administrador.agregarObservador(ceo);

        administrador.persistirProntuario(prontuario());
        administrador.eliminarObservador(ceo);

        assertEquals(1, ceo.cantidadProntuariosInformados());
    }

    @Test
    @DisplayName("T76 Una excusa inverosímil recorre toda la línea y termina en un prontuario informado")
    void t76_integracionCompleta() {
        LineaDeEncargadosI linea = new Constructor()
                .agregar(new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal())))
                .agregar(new SupervisorArea("Lucia", "lucia@excusas.com", 2002, email, new Delivery(new Normal())))
                .agregar(new GerenteRRHH("Pedro", "pedro@excusas.com", 2003, email, new Delivery(new Normal())))
                .agregar(ceo())
                .construir();
        ExcusaI excusa = new Excusa(juan(), new Inverosimil());

        linea.manejar(excusa);

        assertTrue(excusa.estaAceptada());
        assertEquals(1, direccion.cantidadAvisosRecibidos());
    }

    // ----- Ayudas -----

    private CEO ceo() {
        return new CEO("Marta", "marta@excusas.com", 2004, email, new Delivery(new Normal()));
    }

    private EmpleadoI juan() {
        return new Empleado("Juan", "juan@excusas.com", 1001);
    }

    private ProntuarioI prontuario() {
        EmpleadoI juan = juan();
        return new Prontuario(juan, new Excusa(juan, new Inverosimil()));
    }
}
