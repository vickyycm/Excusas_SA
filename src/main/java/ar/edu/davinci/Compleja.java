package ar.edu.davinci;

public class Compleja implements TipoExcusa {

    @Override
    public void serManejadaPor(EncargadoI encargado, ExcusaI excusa) {
        encargado.manejarCompleja(excusa);
    }

    @Override
    public void procesar(EncargadoI encargado, ExcusaI excusa) {
    }
}
