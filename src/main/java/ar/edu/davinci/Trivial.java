package ar.edu.davinci;

public class Trivial implements TipoExcusa {

    @Override
    public void serManejadaPor(EncargadoI encargado, ExcusaI excusa) {
        encargado.manejarTrivial(excusa);
    }

    @Override
    public void procesar(EncargadoI encargado, ExcusaI excusa) {
    }
}
