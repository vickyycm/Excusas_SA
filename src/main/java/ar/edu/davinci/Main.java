package ar.edu.davinci;

import java.util.List;

public class Main implements Runnable {

    private final Email email = new EmailSender();
    private final EquipoDireccion direccion = new EquipoDireccion();

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void run() {
        AdministradorProntuario.getInstancia().agregarObservador(direccion);

        System.out.println("(Arranca en Normal - al procesar la primera excusa pasan a Productivo"
                + " porque llevan menos de 5, y desde ahí avisan al CTO cada vez que derivan)");
        LineaDeEncargadosI linea = new Constructor()
                .agregar(new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal())))
                .agregar(new SupervisorArea("Lucia", "lucia@excusas.com", 2002, email, new Delivery(new Normal())))
                .agregar(new GerenteRRHH("Pedro", "pedro@excusas.com", 2003, email, new Delivery(new Normal())))
                .agregar(new CEO("Marta", "marta@excusas.com", 2004, email, new Delivery(new Normal())))
                .construir();

        Empleado juan = new Empleado("Juan", "juan@excusas.com", 1001);
        List.of(
                new Excusa(juan, new Trivial()),
                new Excusa(juan, new CorteLuz()),
                new Excusa(juan, new CuidadoFamiliar()),
                new Excusa(juan, new Compleja()),
                new Excusa(juan, new Inverosimil())
        ).forEach(excusa -> procesar(linea, excusa));

        LineaDeEncargadosI lineaSinCeo = new Constructor()
                .agregar(new Recepcionista("Ana", "ana@excusas.com", 2001, email, new Delivery(new Normal())))
                .agregar(new GerenteRRHH("Pedro", "pedro@excusas.com", 2003, email, new Delivery(new Normal())))
                .construir();
        procesar(lineaSinCeo, new Excusa(juan, new Inverosimil()));

        Recepcionista productiva = new Recepcionista("Sol", "sol@excusas.com", 3001, email, new Delivery(new Productivo()));
        procesar(new Constructor().agregar(productiva).construir(), new Excusa(juan, new Compleja()));

        Recepcionista vaga = new Recepcionista("Leo", "leo@excusas.com", 3002, email, new Delivery(new Vago()));
        procesar(new Constructor().agregar(vaga).construir(), new Excusa(juan, new Trivial()));

        vaga.tomarCafe();
        System.out.println("Modo actual de Leo: " + vaga.getDelivery().getModo().getClass().getSimpleName());
        procesar(new Constructor().agregar(vaga).construir(), new Excusa(juan, new Trivial()));

        System.out.println("Prontuarios guardados: " + AdministradorProntuario.getInstancia().cantidadProntuarios());
        System.out.println("Avisos recibidos por la dirección: " + direccion.cantidadAvisosRecibidos());
    }

    private void procesar(LineaDeEncargadosI linea, ExcusaI excusa) {
        System.out.println("> Excusa " + excusa.getTipo().getClass().getSimpleName()
                + " de " + excusa.getEmpleado().getNombre());
        linea.manejar(excusa);
        System.out.println("  Aceptada: " + excusa.estaAceptada() + " " + excusa.getRespuesta());
    }

    private void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}