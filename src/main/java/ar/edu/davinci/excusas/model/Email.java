package ar.edu.davinci.excusas.model;

public interface Email {
    void enviarEmail(String unEmailDestino, String unEmailOrigen, String unAsunto, String unCuerpo);
}
