package ar.edu.davinci;

//siempre es un corte de luz o un cuidado familiar
public abstract class Moderada implements TipoExcusa {

    @Override
    public void serManejadaPor(EncargadoI encargado, ExcusaI excusa) {
        encargado.manejarModerada(excusa);
    }
}
