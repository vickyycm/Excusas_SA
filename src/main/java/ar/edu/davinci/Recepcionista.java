package ar.edu.davinci;

public class Recepcionista extends Encargado {

    public Recepcionista(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery);
    }

    //triviales me quedé dormido Y perdí el colectivo
    @Override
    public void manejarTrivial(ExcusaI excusa) {
        procesar(excusa);
    }

    @Override
    protected void notificar(ExcusaI excusa) {
        enviarEmail(excusa.getEmpleado().getEmail(), "motivo demora", "la licencia fue aceptada");
    }
}
