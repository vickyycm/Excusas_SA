package ar.edu.davinci;

//CHAIN OF RESPONSIBILITY
public interface EncargadoI {
    void manejar(ExcusaI excusa);
    void revisarExcusa(ExcusaI excusa);
    void setSiguiente(EncargadoI siguiente);

    //elige cuál de estos llamar
    void manejarTrivial(ExcusaI excusa);
    void manejarModerada(ExcusaI excusa);
    void manejarCompleja(ExcusaI excusa);
    void manejarInverosimil(ExcusaI excusa);

    void enviarEmail(String destino, String asunto, String cuerpo);
}
