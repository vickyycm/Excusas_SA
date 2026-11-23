package ar.edu.davinci.excusas.model;

import java.util.Set;

public class Recepcionista extends Encargado {

    //triviales me quedé dormido Y perdí el colectivo
    public Recepcionista(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery,
                Set.of(Motivo.QUEDARSE_DORMIDO, Motivo.PERDIDA_TRANSPORTE));
    }

    @Override
    protected void notificar(ExcusaI excusa) {
        enviarEmail(excusa.getEmpleado().getEmail(), "motivo demora", "la licencia fue aceptada");
    }
}
