package ar.edu.davinci;

public class SupervisorArea extends Encargado {

    public SupervisorArea(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery);
    }

    //moderadas corte de luz o cuidado de un familiar
    @Override
    public void manejarModerada(ExcusaI excusa) {
        procesar(excusa);
    }

    @Override
    protected void notificar(ExcusaI excusa) {
        excusa.getTipo().procesar(this, excusa);
    }
}
