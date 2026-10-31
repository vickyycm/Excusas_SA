package ar.edu.davinci;

import java.util.List;

//la pasa directo al que sigue
public class Vago implements Modo {

    private final Azar azar;
    private final List<Modo> alVolver = List.of(new Productivo(), new Normal());

    public Vago() {
        this(new AzarAleatorio());
    }

    public Vago(Azar azar) {
        this.azar = azar;
    }

    @Override
    public void tratar(Encargado encargado, ExcusaI excusa) {
        encargado.pasarAlSiguiente(excusa);
    }

    @Override
    public void noPuedeProcesar(Encargado encargado, ExcusaI excusa) {
        encargado.pasarAlSiguiente(excusa);
    }

    @Override
    public void volverAlDiaSiguiente(DeliveryI delivery) {
        delivery.cambiarModo(alVolver.get(azar.elegir(alVolver.size())));
    }
}
