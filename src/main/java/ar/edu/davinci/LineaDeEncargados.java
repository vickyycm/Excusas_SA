package ar.edu.davinci;

//entrega cada excusa
public class LineaDeEncargados implements LineaDeEncargadosI {

    private final EncargadoI primero;

    public LineaDeEncargados(EncargadoI primero) {
        this.primero = primero;
    }

    @Override
    public void manejar(ExcusaI excusa) {
        primero.manejar(excusa);
    }
}
