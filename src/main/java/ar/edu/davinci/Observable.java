package ar.edu.davinci;

//PBSERVER
public interface Observable {
    void agregarObservador(Observador observador);
    void eliminarObservador(Observador observador);
    void notificar(ProntuarioI prontuario);
}
