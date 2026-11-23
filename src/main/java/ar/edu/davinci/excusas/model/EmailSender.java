package ar.edu.davinci.excusas.model;

import org.springframework.stereotype.Component;

@Component
public class EmailSender implements Email {

    @Override
    public void enviarEmail(String unEmailDestino, String unEmailOrigen, String unAsunto, String unCuerpo) {
        System.out.println("Para: " + unEmailDestino + " | De: " + unEmailOrigen
                + " | Asunto: " + unAsunto + " | " + unCuerpo);
    }
}
