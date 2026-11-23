package ar.edu.davinci.excusas.model;

import java.util.Set;

//encargado nuevo que llega por la api con los motivos que maneja
public class EncargadoConfigurable extends Encargado {

    public EncargadoConfigurable(String nombre, String emailEmpleado, int numeroLegajo, Email email,
                                 DeliveryI delivery, Set<Motivo> motivos) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery, motivos);
    }

    @Override
    protected void notificar(ExcusaI excusa) {
        enviarEmail(excusa.getEmpleado().getEmail(), "motivo demora", "Excusa aceptada por " + getNombre());
    }
}
