package ar.edu.davinci;

//BUILDER
public interface TipoExcusa {
    void serManejadaPor(EncargadoI encargado, ExcusaI excusa);

    void procesar(EncargadoI encargado, ExcusaI excusa);
}
