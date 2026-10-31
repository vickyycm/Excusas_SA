package ar.edu.davinci;

public interface DeliveryI {
    void entregar(Encargado encargado, ExcusaI excusa);
    void noPuedeProcesar(Encargado encargado, ExcusaI excusa);
    void excusaProcesada();
    void cambiarModo(Modo modo);
    Modo getModo();
    int getExcusasProcesadas();
    void tomarCafe();
    void tomarBreak(int minutos);
    void volverAlDiaSiguiente();
}
