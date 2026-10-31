package ar.edu.davinci;

public class EmailSender implements Email {

    @Override
    public void enviarEmail(String unEmailDestino, String unEmailOrigen, String unAsunto, String unCuerpo) {
        System.out.println("Para: " + unEmailDestino + " | De: " + unEmailOrigen
                + " | Asunto: " + unAsunto + " | " + unCuerpo);
    }
}
