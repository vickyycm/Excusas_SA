package ar.edu.davinci;

//STRATEGY
public interface Modo {
    void tratar(Encargado encargado, ExcusaI excusa);

    void noPuedeProcesar(Encargado encargado, ExcusaI excusa);

    void volverAlDiaSiguiente(DeliveryI delivery);
}
