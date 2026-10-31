package ar.edu.davinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryTest {

    @Test
    @DisplayName("T50 El delivery arranca con el modo inicial que se le indica")
    void t50_arrancaConModoInicial() {
        Modo vago = new Vago();

        assertSame(vago, new Delivery(vago).getModo());
    }

    @Test
    @DisplayName("T51 Al tomar un café vuelve a estar productivo")
    void t51_cafeProductivo() {
        DeliveryI delivery = new Delivery(new Vago());

        delivery.tomarCafe();

        assertModo(Productivo.class, delivery);
    }

    @Test
    @DisplayName("T52 Un break de 12 minutos lo pone normal")
    void t52_break12Normal() {
        DeliveryI delivery = new Delivery(new Vago());

        delivery.tomarBreak(12);

        assertModo(Normal.class, delivery);
    }

    @Test
    @DisplayName("T53 Un break de exactamente 10 o 15 minutos también surte efecto")
    void t53_breakEnLosBordes() {
        DeliveryI diez = new Delivery(new Vago());
        DeliveryI quince = new Delivery(new Vago());

        diez.tomarBreak(10);
        quince.tomarBreak(15);

        assertModo(Normal.class, diez);
        assertModo(Normal.class, quince);
    }

    @Test
    @DisplayName("T54 Un break de menos de 10 minutos no surte efecto")
    void t54_breakCortoSinEfecto() {
        DeliveryI delivery = new Delivery(new Vago());

        delivery.tomarBreak(5);

        assertModo(Vago.class, delivery);
    }

    @Test
    @DisplayName("T55 Un break de más de 15 minutos no surte efecto")
    void t55_breakLargoSinEfecto() {
        DeliveryI delivery = new Delivery(new Vago());

        delivery.tomarBreak(20);

        assertModo(Vago.class, delivery);
    }

    @Test
    @DisplayName("T56 Con menos de 5 excusas procesadas está productivo")
    void t56_menosDe5Productivo() {
        assertModo(Productivo.class, procesar(4));
    }

    @Test
    @DisplayName("T57 Con 5 excusas procesadas pasa a normal")
    void t57_con5Normal() {
        assertModo(Normal.class, procesar(5));
    }

    @Test
    @DisplayName("T58 Con 10 excusas procesadas sigue normal")
    void t58_con10Normal() {
        assertModo(Normal.class, procesar(10));
    }

    @Test
    @DisplayName("T59 Con 11 excusas procesadas pasa a vago")
    void t59_con11Vago() {
        assertModo(Vago.class, procesar(11));
    }

    @Test
    @DisplayName("T60 Con muchas excusas procesadas sigue vago")
    void t60_conMuchasSigueVago() {
        assertModo(Vago.class, procesar(30));
    }

    @Test
    @DisplayName("T61 Estando vago, al otro día puede volver productivo")
    void t61_vagoVuelveProductivo() {
        DeliveryI delivery = new Delivery(new Vago(cantidad -> 0));

        delivery.volverAlDiaSiguiente();

        assertModo(Productivo.class, delivery);
    }

    @Test
    @DisplayName("T62 Estando vago, al otro día puede volver normal")
    void t62_vagoVuelveNormal() {
        DeliveryI delivery = new Delivery(new Vago(cantidad -> 1));

        delivery.volverAlDiaSiguiente();

        assertModo(Normal.class, delivery);
    }

    @Test
    @DisplayName("T63 Estando normal, volver al otro día no cambia el modo")
    void t63_normalSigueNormal() {
        Modo normal = new Normal();
        DeliveryI delivery = new Delivery(normal);

        delivery.volverAlDiaSiguiente();

        assertSame(normal, delivery.getModo());
    }

    @Test
    @DisplayName("T64 El azar real elige una posición válida")
    void t64_azarRealEnRango() {
        int elegido = new AzarAleatorio().elegir(2);

        assertTrue(elegido >= 0 && elegido < 2);
    }

    @Test
    @DisplayName("T65 El encargado le delega el café, el break y el día siguiente a su delivery")
    void t65_encargadoDelega() {
        DeliveryI delivery = mock(DeliveryI.class);
        Encargado recepcionista = new Recepcionista("Ana", "ana@excusas.com", 2001, mock(Email.class), delivery);

        recepcionista.tomarCafe();
        recepcionista.tomarBreak(12);
        recepcionista.volverAlDiaSiguiente();

        verify(delivery).tomarCafe();
        verify(delivery).tomarBreak(12);
        verify(delivery).volverAlDiaSiguiente();
    }

    private DeliveryI procesar(int cantidad) {
        DeliveryI delivery = new Delivery(new Productivo());
        for (int i = 0; i < cantidad; i++) {
            delivery.excusaProcesada();
        }
        return delivery;
    }

    private void assertModo(Class<?> esperado, DeliveryI delivery) {
        assertEquals(esperado, delivery.getModo().getClass());
    }
}
