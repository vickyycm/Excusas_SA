package ar.edu.davinci.excusas.model;

import java.util.Set;

public class GerenteRRHH extends Encargado {

    //complejas
    public GerenteRRHH(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery, Set.of(Motivo.IRRELEVANTE));
    }

    @Override
    protected void notificar(ExcusaI excusa) {
    }
}
