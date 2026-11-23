package ar.edu.davinci.excusas.model;

//STRATEGY
public interface Modo {
    void tratar(Encargado encargado, ExcusaI excusa);

    void noPuedeProcesar(Encargado encargado, ExcusaI excusa);

    void volverAlDiaSiguiente(DeliveryI delivery);

    ModoTrabajo getModoTrabajo();
}
