package ar.edu.davinci.excusas.model;

import java.util.Set;

public class SupervisorArea extends Encargado {

    //moderadas corte de luz o cuidado de un familiar
    public SupervisorArea(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery,
                Set.of(Motivo.PERDIDA_SUMINISTRO, Motivo.CUIDADO_FAMILIAR));
    }

    @Override
    protected void notificar(ExcusaI excusa) {
        excusa.getTipo().procesar(this, excusa);
    }
}
