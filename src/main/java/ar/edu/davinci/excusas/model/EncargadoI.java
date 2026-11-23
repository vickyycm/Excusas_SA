package ar.edu.davinci.excusas.model;

import java.util.Set;

//CHAIN OF RESPONSIBILITY
public interface EncargadoI {
    void manejar(ExcusaI excusa);
    void revisarExcusa(ExcusaI excusa);
    void setSiguiente(EncargadoI siguiente);
    void enviarEmail(String destino, String asunto, String cuerpo);
    String getNombre();
    Set<Motivo> getMotivos();
}
