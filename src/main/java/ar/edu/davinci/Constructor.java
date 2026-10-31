package ar.edu.davinci;

import java.util.ArrayList;
import java.util.List;

public class Constructor implements LineaBuild {

    private final List<EncargadoI> encargados = new ArrayList<>();

    @Override
    public LineaBuild agregar(EncargadoI encargado) {
        encargados.add(encargado);
        return this;
    }

    //Fila de encargados y el ultimo llama al rechazador
    @Override
    public LineaDeEncargadosI construir() {
        EncargadoI siguiente = new Rechazador();
        for (int i = encargados.size() - 1; i >= 0; i--) {
            EncargadoI actual = encargados.get(i);
            actual.setSiguiente(siguiente);
            siguiente = actual;
        }
        return new LineaDeEncargados(siguiente);
    }
}
