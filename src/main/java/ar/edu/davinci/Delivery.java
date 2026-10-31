package ar.edu.davinci;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//STRATEGY
public class Delivery implements DeliveryI {

    private static final int TOPE_TABLA = 11;

    private Modo modo;
    private int excusasProcesadas;
    private final List<Modo> modoPorCantidad;
    private final Map<Integer, Modo> efectoDelBreak;

    public Delivery(Modo modoInicial) {
        this.modo = modoInicial;
        this.excusasProcesadas = 0;

        Modo productivo = new Productivo();
        Modo normal = new Normal();
        this.modoPorCantidad = List.of(
                productivo, productivo, productivo, productivo, productivo,
                normal, normal, normal, normal, normal, normal,
                new Vago());

        this.efectoDelBreak = new HashMap<>();
        for (int minutos = 10; minutos <= 15; minutos++) {
            efectoDelBreak.put(minutos, normal);
        }
    }

    @Override
    public void entregar(Encargado encargado, ExcusaI excusa) {
        modo.tratar(encargado, excusa);
    }

    @Override
    public void noPuedeProcesar(Encargado encargado, ExcusaI excusa) {
        modo.noPuedeProcesar(encargado, excusa);
    }

    @Override
    public void excusaProcesada() {
        excusasProcesadas++;
        modo = modoPorCantidad.get(Math.min(excusasProcesadas, TOPE_TABLA));
    }

    @Override
    public void cambiarModo(Modo nuevoModo) {
        this.modo = nuevoModo;
    }

    @Override
    public Modo getModo() {
        return modo;
    }

    @Override
    public int getExcusasProcesadas() {
        return excusasProcesadas;
    }

    @Override
    public void tomarCafe() {
        modo = new Productivo();
    }

    @Override
    public void tomarBreak(int minutos) {
        modo = efectoDelBreak.getOrDefault(minutos, modo);
    }

    @Override
    public void volverAlDiaSiguiente() {
        modo.volverAlDiaSiguiente(this);
    }
}
