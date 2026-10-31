package ar.edu.davinci;

public class Normal implements Modo {

    @Override
    public void tratar(Encargado encargado, ExcusaI excusa) {
        encargado.evaluar(excusa);
    }

    @Override
    public void noPuedeProcesar(Encargado encargado, ExcusaI excusa) {
        encargado.pasarAlSiguiente(excusa);
    }

    @Override
    public void volverAlDiaSiguiente(DeliveryI delivery) {
    }
}
