package ar.edu.davinci;

import java.util.Random;

public class AzarAleatorio implements Azar {

    private final Random random = new Random();

    @Override
    public int elegir(int cantidad) {
        return random.nextInt(cantidad);
    }
}
