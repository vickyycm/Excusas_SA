package ar.edu.davinci;

public class Inverosimil implements TipoExcusa {

    @Override
    public void serManejadaPor(EncargadoI encargado, ExcusaI excusa) {
        encargado.manejarInverosimil(excusa);
    }

    @Override
    public void procesar(EncargadoI encargado, ExcusaI excusa) {
    }
}
